package com.example.springbootshop.rest.exception.receipt;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) /*404*/
public class ReceiptNotFoundException extends RuntimeException {

    public ReceiptNotFoundException() {
        super("Receipt is not found.");
    }

    public ReceiptNotFoundException(int receiptNo) {
        super("Receipt is not found (receiptNo=" + receiptNo + ")");
    }
}
