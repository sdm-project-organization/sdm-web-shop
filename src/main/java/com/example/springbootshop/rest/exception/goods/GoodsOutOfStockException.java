package com.example.springbootshop.rest.exception.goods;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE) /*410*/
public class GoodsOutOfStockException extends RuntimeException {

    public GoodsOutOfStockException() {
        super("Out of Stock.");
    }
}
