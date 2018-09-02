package com.example.springbootshop.rest.exception.purchase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) /*400*/
public class PurchaseDataIntegrityViolationException extends RuntimeException {

    public PurchaseDataIntegrityViolationException() {
        super("Purchase data integration occured.");
    }
}
