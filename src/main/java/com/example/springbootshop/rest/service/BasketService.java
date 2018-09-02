package com.example.springbootshop.rest.service;

import com.example.springbootshop.common.util.SortUtil;
import com.example.springbootshop.rest.exception.basket.*;
import com.example.springbootshop.rest.exception.goods.GoodsNotFountException;
import com.example.springbootshop.rest.exception.goods.GoodsOutOfStockException;
import com.example.springbootshop.rest.model.entity.Basket;
import com.example.springbootshop.rest.model.entity.Goods;
import com.example.springbootshop.rest.repository.BasketRepository;
import com.example.springbootshop.rest.repository.GoodsRepository;
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
public class BasketService {

    private static final Logger log = LoggerFactory.getLogger(BasketService.class);

    @Autowired
    BasketRepository basketRepository;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    UserService userService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    SortUtil sortUtil;

    @Value("${basket.pagenation.item.size}")
    int size;

    @Value("${basket.item.count.max}")
    private int maxItemCount;

    @Value("${basket.item.count.min}")
    private int minItemCount;

    @Value("${basket.create.count.max}")
    private int maxBasketCount;

    // == Basket 개수 (User) ==
    public long countByUserNo() {
        int userNo = userService.getShopUserDetails().getUserNo(); // == 인증유저 ==

        return basketRepository
                .countByUserNoAndEnable(userNo, true);
    }

    // == Basket 조회 ==
    public Basket findByBasketNo(int basketNo) {
        Basket basket = basketRepository
                .findByBasketNoAndEnable(basketNo, true);

        if(basket == null)
            throw new GoodsNotFountException();

        return basket;
    }

    // == Basket 전체조회 (Pagenation) ==
    public List<Basket> findAllByPage(int page) {
        Pageable pageable = new PageRequest(page, size, sortUtil.getDescCreatedAtSort());
        Page<Basket> pageBasket = basketRepository
                .findAllByEnable(true, pageable);

        if (pageBasket.getContent().size() == 0)
            throw new BasketNotFoundException();

        return pageBasket.getContent();
    }

    // == Basket 전체조회 (User + Pagenation) ==
    public List<Basket> findAllByUserNoAndPage(int page) {
        int userNo = userService.getShopUserDetails().getUserNo(); // == 인증유저 ==

        Pageable pageable = new PageRequest(page, size, sortUtil.getDescCreatedAtSort());
        Page<Basket> pageBasket = basketRepository
                .findAllByUserNoAndEnable(userNo, true, pageable);

        if (pageBasket.getContent().size() == 0)
            throw new BasketNotFoundException();

        return pageBasket.getContent();
    }

    // == Basket 전체 조회 (User) ==
    public List<Basket> findAllByUserNo() {
        int userNo = userService.getShopUserDetails().getUserNo(); // == 인증유저 ==

        List<Basket> listBasket = basketRepository
                .findAllByUserNoAndEnable(userNo, true);

        if (listBasket.size() == 0)
            throw new BasketNotFoundException();

        return listBasket;
    }

    // == Basket 저장 ==
    public Basket save(Basket basket) {
        int userNo = userService.getShopUserDetails().getUserNo(); // == 인증유저 ==

        // == 초기설정 ==
        basket.setCount(1);

        // == 최대용량 == /*416*/
        if (countByUserNo() > maxBasketCount)
            throw new BasketCountOverflowException();

        // == 재고확인 == /*404*/
        goodsService.getRemainingStock(
                basket.getGoodsNo(),
                basket.getCount());

        // == 중복저장 == /*409*/
        if (basketRepository
                .findByUserNoAndGoodsNoAndEnable(userNo, basket.getGoodsNo(), true) != null)
            throw new BasketAlreadyExistException();

        basket.setUserNo(userNo);

        try {
            return basketRepository.save(basket);
        } catch (DataIntegrityViolationException e) {
            throw new BasketDataIntegrityViolationException();
        }
    }

    // == Basket 수정 ==
    public Basket updateCount(int basketNo, Basket newBasket) {
        Basket originBasket = this
                .findByBasketNo(basketNo);

        // == 범위확인 == /*416*/
        checkBasketRange(newBasket.getCount());

        // == 재고확인 == /*410*/
        goodsService.getRemainingStock(
                originBasket.getGoodsNo(),
                newBasket.getCount());

        originBasket.setCount(newBasket.getCount());

        try {
            basketRepository.flush();
            return originBasket;
        } catch (DataIntegrityViolationException e) {
            throw new BasketDataIntegrityViolationException();
        }
    }

    // == Basket 삭제 (논리삭제) ==
    public Basket delete(int basketNo) {
        Basket originBasket = this
                .findByBasketNo(basketNo);

        originBasket.setEnable(false);

        try {
            basketRepository.flush();
            return originBasket;
        } catch (DataIntegrityViolationException e) {
            throw new BasketDataIntegrityViolationException();
        }
    }

    // == 수량을 제외한 재고 ==
    public void checkBasketRange(int basketCount) {
        if (basketCount < minItemCount) throw new BasketItemCountUnderflowException();
        if (basketCount > maxItemCount) throw new BasketItemCountOverflowException();
    }

}
