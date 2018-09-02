package com.example.springbootshop.rest.exception.purchase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) /*404*/
public class PurchaseNotFoundException extends RuntimeException {

    public PurchaseNotFoundException() {
        super("Purchase is not found.");
    }

    public PurchaseNotFoundException(int purchaseNo) {
        super("Purchase is not found (purchaseNo=" + purchaseNo + ")");
    }
}
