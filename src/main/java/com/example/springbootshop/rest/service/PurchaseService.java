package com.example.springbootshop.rest.service;

import com.example.springbootshop.common.util.SortUtil;
import com.example.springbootshop.rest.exception.basket.BasketNotFoundException;
import com.example.springbootshop.rest.exception.goods.GoodsNotFountException;
import com.example.springbootshop.rest.exception.goods.GoodsOutOfStockException;
import com.example.springbootshop.rest.exception.purchase.*;
import com.example.springbootshop.rest.exception.receipt.ReceiptNotFoundException;
import com.example.springbootshop.rest.model.body.PurchaseBody;
import com.example.springbootshop.rest.model.entity.Basket;
import com.example.springbootshop.rest.model.entity.Goods;
import com.example.springbootshop.rest.model.entity.Purchase;
import com.example.springbootshop.rest.model.entity.Receipt;
import com.example.springbootshop.rest.repository.PurchaseRepository;
import com.example.springbootshop.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {

    private static final Logger log = LoggerFactory.getLogger(PurchaseService.class);

    @Autowired
    PurchaseRepository purchaseRepository;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    BasketService basketService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    UserService userService;

    @Autowired
    SortUtil sortUtil;

    @Value("${purchase.pagenation.item.size}")
    int sizeItem;

    // == Purchase 개수 (User) ==
    public long countByUserNo() {
        int userNo = userService.getShopUserDetails().getUserNo(); // == 인증유저 ==

        return purchaseRepository
                .countByUserNoAndEnable(userNo, true);
    }

    // == Purchase 조회 ==
    public Purchase findByPurchaseNo(int purchaseNo) {
        Purchase purchase = purchaseRepository
                .findByPurchaseNoAndEnable(purchaseNo, true);

        if(purchase == null)
            throw new PurchaseNotFoundException();

        return purchaseRepository
                .findByPurchaseNoAndEnable(purchaseNo, true);
    }

    // == Purchase 전체조회 (Pagenation) ==
    public List<Purchase> findAllByPage(int page) {
        Pageable pageable = new PageRequest(page, sizeItem, sortUtil.getDescCreatedAtSort());
        Page<Purchase> pagePurchase = purchaseRepository
                .findAllByEnable(true, pageable);

        if(pagePurchase.getContent().size() == 0)
            throw new PurchaseNotFoundException();

        return pagePurchase.getContent();
    }

    // == Purchase 전체조회 (User + Pagenation) ==
    public List<Purchase> findAllByUserNoAndPage(int page) {
        int userNo = userService.getShopUserDetails().getUserNo(); // == 인증유저 ==

        Pageable pageable = new PageRequest(page, sizeItem, sortUtil.getDescCreatedAtSort());
        Page<Purchase> pagePurchase = purchaseRepository
                .findAllByUserNoAndEnable(userNo, true, pageable);

        if(pagePurchase.getContent().size() == 0)
            throw new PurchaseNotFoundException();

        return pagePurchase.getContent();
    }

    // == Purchase 저장 ==
    public Purchase save(PurchaseBody purchaseBody) {
        int userNo = userService.getShopUserDetails().getUserNo(); // == 인증유저 ==

        Purchase purchase = new Purchase();
        purchase.setUserNo(userNo);
        purchase.setTel(purchaseBody.getTel());
        purchase.setAddress(purchaseBody.getAddress());
        Purchase newPurchase = null;

        try {
            newPurchase = purchaseRepository.save(purchase);
        } catch (DataIntegrityViolationException e) {
            throw new PurchaseDataIntegrityViolationException();
        }

        for(Basket basket : basketService.findAllByUserNo()) {
            int basketNo = basket.getBasketNo();
            int count = basket.getCount();
            int goodsNo;

            // == Basket delete() ==
            Basket deleteBasket = basketService.delete(basketNo);
            goodsNo = deleteBasket.getGoodsNo();

            // == Goods update() == /*410*/
            int stock = goodsService.getRemainingStock(goodsNo, count);
            goodsService.updateStock(goodsNo, stock);

            // == Receipt save() ==
            Receipt receipt = new Receipt(newPurchase.getPurchaseNo(), goodsNo, count);
            receiptService.save(receipt);
        }

        // == 가상결제모듈처리 ==
        // 1) 정상적인 값이 넘어온다면 Commit (결제완료)
        // 2) 정상적인 값이 넘어오지 않았다면 Rollback (결제실패)

        if(true) {
            purchase.setPayment(true);

            try {
                purchaseRepository.flush();
                return newPurchase;
            } catch (DataIntegrityViolationException e) {
                throw new PurchaseDataIntegrityViolationException();
            }
        } else {
            throw new PurchasePaymentException();
            // TODO == Rollback 처리로직 ==
        }
    }

    // == Purchase 수정 (구매취소) ==
    public Purchase updateCancel(int purchaseNo) {
        Purchase originPurchase = this
                .findByPurchaseNo(purchaseNo);

        if(originPurchase.isDelivery()) // == Exception 배송출발 ==
            throw new PurchaseAlreadyDeliveryException();

        if(!originPurchase.isActive()) // == ExceptionLog 처리 (발생할수없는요청) ==
            throw new PurchaseAlreadyCancelException();

        for(Receipt receipt : originPurchase.getReceipts()) {

            // == Goods update() ==
            Goods goods = goodsService
                    .findByGoodsNo(receipt.getGoodsNo());
            goodsService.updateStock(receipt.getGoodsNo(), goods.getStock() + receipt.getCount());
        }

        // == 가상결제모듈처리 ==
        // 1) 정상적인 값이 넘어온다면 Commit (환불완료)
        // 2) 정상적인 값이 넘어오지 않았다면 Rollback (환불실패)

        if(true) {
            originPurchase.setActive(false); // == 활성상태 ==
            originPurchase.setPayment(false); // == 결제상태 ==
        } else {
            throw new PurchasePaymentException();
            // TODO == Rollback 처리로직 ==
        }

        originPurchase.setPayment(false);

        try {
            purchaseRepository.flush();
            return originPurchase;
        } catch (DataIntegrityViolationException e) {
            throw new PurchaseDataIntegrityViolationException();
        }
    }

}
