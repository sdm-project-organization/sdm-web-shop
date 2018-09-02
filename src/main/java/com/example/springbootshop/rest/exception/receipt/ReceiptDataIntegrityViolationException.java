package com.example.springbootshop.rest.exception.receipt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) /*404*/
public class ReceiptDataIntegrityViolationException extends RuntimeException {

    public ReceiptDataIntegrityViolationException() {
        super("Receipt data integration occured.");
    }
}
