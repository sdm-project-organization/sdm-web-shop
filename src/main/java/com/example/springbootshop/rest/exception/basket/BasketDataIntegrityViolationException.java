package com.example.springbootshop.rest.exception.basket;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) /*400*/
public class BasketDataIntegrityViolationException extends DataIntegrityViolationException {

    public BasketDataIntegrityViolationException() {
        super("Basket data integration occured.");
    }
}
