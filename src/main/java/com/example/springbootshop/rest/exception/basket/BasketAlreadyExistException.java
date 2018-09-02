package com.example.springbootshop.rest.exception.basket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) /*409*/
public class BasketAlreadyExistException extends RuntimeException {

    public BasketAlreadyExistException() {
        super("Basket already exist.");
    }
}
