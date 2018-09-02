package com.example.springbootshop.rest.exception.purchase;

// TODO
public class PurchasePaymentException extends RuntimeException {

    public PurchasePaymentException() {
        super("Payment failed.");
    }
}
