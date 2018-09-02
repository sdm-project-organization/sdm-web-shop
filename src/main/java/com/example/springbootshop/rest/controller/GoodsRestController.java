package com.example.springbootshop.rest.controller;

import com.example.springbootshop.common.util.MessageUtil;
import com.example.springbootshop.rest.exception.goods.GoodsNotFountException;
import com.example.springbootshop.rest.model.entity.Goods;
import com.example.springbootshop.rest.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsRestController {

    private static final Logger log = LoggerFactory.getLogger(GoodsRestController.class);

    @Autowired
    GoodsService goodsService;

    // == [GET] /api/goods/{goodsNo} (USER, EMPLOYEE, ADMIN) ==
    @RequestMapping(path = "{goodsNo}", method = RequestMethod.GET)
    public Goods getGoods(
            @PathVariable int goodsNo
    ) {
        Goods goods = goodsService.findByGoodsNo(goodsNo);
        if(goods == null) throw new GoodsNotFountException(goodsNo);
        return goods;
    }

    // == [GET] /api/goods?page={num} (USER, EMPLOYEE, ADMIN) ==
    @RequestMapping(method = RequestMethod.GET)
    public List<Goods> getAllGoods(
            @RequestParam int page
    ) {
        List<Goods> listGoods = goodsService.findAllByPage(page);
        return listGoods;
    }

    // == [POST] /api/goods (EMPLOYEE, ADMIN) ==
    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> createGoods(
            @Validated @RequestBody Goods goods,
            UriComponentsBuilder uriBuilder
    ) {
        Goods newGoods = goodsService.save(goods);

        URI resourceUri = uriBuilder // == RES URI ==
                .path("/api/goods/{goodsNo}")
                .buildAndExpand(newGoods.getGoodsNo())
                .encode()
                .toUri();

        return ResponseEntity.created(resourceUri).build();
    }

    // == [PUT] /api/goods/{goodsNo} (EMPLOYEE, ADMIN) ==
    @RequestMapping(path = "{goodsNo}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('EMPLOYEE')")
    @ResponseStatus(HttpStatus.NO_CONTENT) /*204*/
    public void updateGoodsInfo(
            @PathVariable int goodsNo,
            @Validated @RequestBody Goods goods
    ) {
        goodsService.updateInfo(goodsNo, goods);
    }

    // == [PUT] /api/goods/price/{goodsNo} (ADMIN) ==
    @RequestMapping(path = "/price/{goodsNo}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) /*204*/
    public void updateGoodsPrice(
            @PathVariable int goodsNo,
            @Validated @RequestBody Goods goods
    ) {
        goodsService.updatePrice(goodsNo, goods.getPrice());
    }

    // == [PUT] /api/goods/stock/{goodsNo} (ADMIN) ==
    @RequestMapping(path = "/stock/{goodsNo}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) /*204*/
    public void updateGoodsStock(
            @PathVariable int goodsNo,
            @Validated @RequestBody Goods goods
    ) {
        goodsService.updateStock(goodsNo, goods.getStock());
    }

    // == [PUT] /api/goods/active/{goodsNo} (ADMIN) ==
    @RequestMapping(path = "/active/{goodsNo}", method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) /*204*/
    public void updateGoodsActive(
            @PathVariable int goodsNo,
            @Validated @RequestBody Goods goods
    ) {
        goodsService.updateActive(goodsNo, goods.isActive());
    }

    // == [DELETE] /api/goods/{goodsNo} (ADMIN)
    @RequestMapping(path = "{goodsNo}", method = RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT) /*204*/
    public void deleteGoods(
            @PathVariable int goodsNo
    ) {
        goodsService.delete(goodsNo);
    }

}
