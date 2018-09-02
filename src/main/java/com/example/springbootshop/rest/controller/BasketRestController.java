package com.example.springbootshop.rest.controller;

import com.example.springbootshop.common.util.MessageUtil;
import com.example.springbootshop.rest.exception.basket.BasketNotFoundException;
import com.example.springbootshop.rest.model.entity.Basket;
import com.example.springbootshop.rest.service.BasketService;
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
@RequestMapping("/api/basket")
public class BasketRestController {

    private static final Logger log = LoggerFactory.getLogger(GoodsRestController.class);

    @Autowired
    BasketService basketService;

    @Autowired
    private MessageUtil messageUtil;

    // == [GET] /api/basket/{basketNo} (EMPLOYEE, ADMIN) ==
    @RequestMapping(path="{basketNo}", method=RequestMethod.GET)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Basket getBasket(
            @PathVariable int basketNo
    ) {
        Basket basket = basketService.findByBasketNo(basketNo);
        return basket;
    }

    // == [GET] /api/basket?page={num} (EMPLOYEE, ADMIN) ==
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<Basket> getAllBasket(
            @RequestParam int page
    ) {
        List<Basket> listBasket = basketService.findAllByPage(page);
        return listBasket;
    }

    // == [GET] /api/basket/user?page={num} (USER, EMPLOYEE, ADMIN) ==
    @RequestMapping(path="/user", method = RequestMethod.GET)
    public List<Basket> getAllUserBasket(
            @RequestParam int page
    ) {
        List<Basket> listBasket = basketService.findAllByUserNoAndPage(page);
        return listBasket;
    }

    // == [POST] /api/basket (USER, EMPLOYEE, ADMIN) ==
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> createBasket(
            @Validated @RequestBody Basket basket,
            UriComponentsBuilder uriBuilder
    ) {
        Basket newBasket = basketService.save(basket);

        URI resourceUri = uriBuilder // == RES URI ==
                .path("/api/basket/{basketNo}")
                .buildAndExpand(newBasket.getBasketNo())
                .encode()
                .toUri();

        return ResponseEntity.created(resourceUri).build();
    }

    // == [PUT] /api/basket/{basketNo} (USER, EMPLOYEE, ADMIN) ==
    @RequestMapping(path="{basketNo}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBasket(
            @PathVariable int basketNo,
            @Validated @RequestBody Basket basket
    ) {
        basketService.updateCount(basketNo, basket);
    }

    // == [DELETE] /api/basket/{basketNo} (USER, EMPLOYEE, ADMIN) ==
    @RequestMapping(path="{basketNo}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBasket(
            @PathVariable int basketNo
    ) {
        basketService.delete(basketNo);
    }

}
