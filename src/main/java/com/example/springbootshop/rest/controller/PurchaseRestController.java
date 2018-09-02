package com.example.springbootshop.rest.controller;

import com.example.springbootshop.rest.exception.purchase.PurchaseNotFoundException;
import com.example.springbootshop.rest.model.body.PurchaseBody;
import com.example.springbootshop.rest.model.entity.Purchase;
import com.example.springbootshop.rest.service.PurchaseService;
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
@RequestMapping("/api/purchase")
public class PurchaseRestController {

    private static final Logger log = LoggerFactory.getLogger(PurchaseRestController.class);

    @Autowired
    PurchaseService purchaseService;

    // == [GET] /api/purchase/{purchaseNo} (EMPLOYEE, ADMIN) ==
    @RequestMapping(path="{purchaseNo}", method=RequestMethod.GET)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public Purchase getPurchase(
            @PathVariable int purchaseNo
    ) {
        Purchase purchase = purchaseService.findByPurchaseNo(purchaseNo);
        return purchase;
    }

    // == [GET] /api/purchase?page={num} (EMPLOYEE, ADMIN) ==
    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<Purchase> getAllPurchase(
            @RequestParam int page
    ) {
        List<Purchase> listPurchase = purchaseService.findAllByPage(page);
        return listPurchase;
    }

    // == [GET] /api/purchase/user?page={num} (USER, EMPLOYEE, ADMIN) ==
    @RequestMapping(path="/user", method = RequestMethod.GET)
    public List<Purchase> getAllUserPurchase(
            @RequestParam int page
    ) {
        List<Purchase> listPurchase = purchaseService.findAllByUserNoAndPage(page);
        return listPurchase;
    }

    // == [POST] /api/purchase (USER) ==
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<Void> createPurchase(
            @Validated @RequestBody PurchaseBody purchaseBody,
            UriComponentsBuilder uriBuilder
    ) {
        Purchase newPurchase = purchaseService.save(purchaseBody);

        URI resourceUri = uriBuilder // == RES URI ==
                .path("/api/purchase/{purchaseNo}")
                .buildAndExpand(newPurchase.getPurchaseNo())
                .encode()
                .toUri();

        return ResponseEntity.created(resourceUri).build();
    }

    // == [PUT] /cancel/{purchaseNo} (USER, EMPLOYEE, ADMIN) ==
    @RequestMapping(path="/cancel/{purchaseNo}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT) /*204*/
    public void cancelPurchase(
            @PathVariable int purchaseNo
    ) {
        purchaseService.updateCancel(purchaseNo);
    }
    
}
