package com.example.springbootshop.rest.exception.basket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) /*404*/
public class BasketNotFoundException extends RuntimeException {

    public BasketNotFoundException() {
        super("Basket is not found.");
    }

    public BasketNotFoundException(int basketNo) {
        super("Basket is not found (basketNo=" + basketNo + ")");
    }

}
