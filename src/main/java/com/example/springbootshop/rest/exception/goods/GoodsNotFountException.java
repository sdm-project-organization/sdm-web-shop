package com.example.springbootshop.rest.exception.goods;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) /*404*/
public class GoodsNotFountException extends RuntimeException {

    public GoodsNotFountException() {
        super("Goods is not found.");
    }

    public GoodsNotFountException(int goodsNo) {
        super("Goods is not found (goodsNo=" + goodsNo + ")");
    }
}
