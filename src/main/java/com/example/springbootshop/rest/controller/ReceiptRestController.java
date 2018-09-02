package com.example.springbootshop.rest.controller;

import com.example.springbootshop.rest.exception.purchase.PurchaseNotFoundException;
import com.example.springbootshop.rest.model.entity.Receipt;
import com.example.springbootshop.rest.service.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptRestController {

    private static final Logger log = LoggerFactory.getLogger(ReceiptRestController.class);

    @Autowired
    ReceiptService receiptService;

    // == [GET] /api/receipt/{receiptNo} (EMPLOYEE, ADMIN) ==
    @RequestMapping(path="{receiptNo}", method=RequestMethod.GET)
    public Receipt getReceipt(
            @PathVariable int receiptNo
    ) {
        Receipt receipt = receiptService.findByReceiptNo(receiptNo);
        return receipt;
    }

}
