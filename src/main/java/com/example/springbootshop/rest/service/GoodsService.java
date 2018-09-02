package com.example.springbootshop.rest.service;

import com.example.springbootshop.common.util.MessageUtil;
import com.example.springbootshop.common.util.SortUtil;
import com.example.springbootshop.rest.controller.GoodsRestController;
import com.example.springbootshop.rest.exception.goods.GoodsDataIntegrityViolationException;
import com.example.springbootshop.rest.exception.goods.GoodsNotFountException;
import com.example.springbootshop.rest.exception.goods.GoodsOutOfStockException;
import com.example.springbootshop.rest.model.entity.Goods;
import com.example.springbootshop.rest.repository.GoodsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Service
public class GoodsService {

    private static final Logger log = LoggerFactory.getLogger(GoodsService.class);

    @Autowired
    MessageUtil messageUtil;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    SortUtil sortUtil;

    @Value("${goods.pagenation.item.size}")
    int size;

    @Value("${dummy.goods.file.name}")
    private String dummyGoodsFileName;

    // == Goods 초기화 ==
    @PostConstruct
    public void init() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Goods> initGoodsList = Arrays.asList(objectMapper.readValue(
                    getClass().getClassLoader().getResourceAsStream(dummyGoodsFileName),
                    Goods[].class));
            saveAll(initGoodsList);
        } catch (Exception e) {
            log.error("[Exception] " + e.getClass() + " : " + e.getMessage());
        }
    }

    // == Goods 개수 ==
    public long countAll() {
        return goodsRepository.count();
    }

    // == Goods 조회 ==
    public Goods findByGoodsNo(int goodsNo) {
        Goods goods = goodsRepository
                .findByGoodsNoAndEnable(goodsNo, true);

        if(goods == null)
            throw new GoodsNotFountException();

        return goods;
    }

    // == Goods 전체조회 (Pagenation) ==
    public List<Goods> findAllByPage(int page) {
        Pageable pageable = new PageRequest(page, size, sortUtil.getDescCreatedAtSort());
        Page<Goods> pageGoods = goodsRepository
                .findAllByEnable(true, pageable);

        if (pageGoods.getContent().size() == 0)
            throw new GoodsNotFountException();

        return pageGoods.getContent();
    }

    // == Goods 저장 ==
    public Goods save(Goods goods) {
        try {
            return goodsRepository.save(goods);
        } catch (DataIntegrityViolationException e) {
            throw new GoodsDataIntegrityViolationException();
        }
    }

    // == Goods 전체저장 ==
    private Iterable<Goods> saveAll(Iterable<Goods> goodsList) {
        try {
            return goodsRepository.saveAll(goodsList);
        } catch (DataIntegrityViolationException e) {
            throw new GoodsDataIntegrityViolationException();
        }
    }

    // == Goods 수정 (내용)  ==
    public Goods updateInfo(int goodsNo, Goods goods) {
        Goods originGoods = this
                .findByGoodsNo(goodsNo);

        originGoods.setTitle(goods.getTitle());
        originGoods.setSubTitle(goods.getSubTitle());
        originGoods.setContents(goods.getContents());
        originGoods.setThumbnail(goods.getThumbnail());

        try {
            goodsRepository.flush();
            return originGoods;
        } catch (DataIntegrityViolationException e) {
            throw new GoodsDataIntegrityViolationException();
        }
    }

    // == Goods 수정 (재고) ==
    public Goods updateStock(int goodsNo, int stock) {
        Goods originGoods = this
                .findByGoodsNo(goodsNo);

        originGoods.setStock(stock);

        try {
            goodsRepository.flush();
            return originGoods;
        } catch (DataIntegrityViolationException e) {
            throw new GoodsDataIntegrityViolationException();
        }
    }

    // == Goods 수정 (가격) ==
    public Goods updatePrice(int goodsNo, int price) {
        Goods originGoods = this
                .findByGoodsNo(goodsNo);

        originGoods.setPrice(price);

        try {
            goodsRepository.flush();
            return originGoods;
        } catch (DataIntegrityViolationException e) {
            throw new GoodsDataIntegrityViolationException();
        }
    }

    // == Goods 수정 (활성) ==
    public Goods updateActive(int goodsNo, boolean active) {
        Goods originGoods = this
                .findByGoodsNo(goodsNo);

        originGoods.setActive(active);

        try {
            goodsRepository.flush();
            return originGoods;
        } catch (DataIntegrityViolationException e) {
            throw new GoodsDataIntegrityViolationException();
        }
    }

    // == Goods 삭제 (논리삭제) ==
    public Goods delete(int goodsNo) {
        Goods originGoods = this
                .findByGoodsNo(goodsNo);

        originGoods.setEnable(false);

        try {
            goodsRepository.flush();
            return originGoods;
        } catch (DataIntegrityViolationException e) {
            throw new GoodsDataIntegrityViolationException();
        }
    }

    // == Goods 재고파악 ==
    public int getRemainingStock(int goodsNo, int basketCount) {
        Goods goods = this
                .findByGoodsNo(goodsNo);

        int remainingStock = goods.getStock() - basketCount; // == 장바구니를 제외한 재고 ==

        if(remainingStock >= 0)
            return remainingStock;
        else
            throw new GoodsOutOfStockException();
    }

}
