package com.example.springbootshop.rest.exception.purchase;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE) /*406*/
public class PurchaseAlreadyDeliveryException extends RuntimeException {

    public PurchaseAlreadyDeliveryException() {
        super("Purchase has already been canceled.");
    }
}
