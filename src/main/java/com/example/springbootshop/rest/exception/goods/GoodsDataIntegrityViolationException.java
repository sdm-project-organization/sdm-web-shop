package com.example.springbootshop.rest.exception.goods;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) /*400*/
public class GoodsDataIntegrityViolationException extends RuntimeException {

    public GoodsDataIntegrityViolationException() {
        super("Goods data integration occured.");
    }
}
