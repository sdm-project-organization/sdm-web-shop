package com.example.springbootshop.rest.exception.basket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE) /*416*/
public class BasketItemCountUnderflowException extends RuntimeException {

    public BasketItemCountUnderflowException() {
        super("Basket is underflow.");
    }
}
