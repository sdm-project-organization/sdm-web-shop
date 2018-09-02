package com.example.springbootshop.rest.service;

import com.example.springbootshop.rest.controller.ReceiptRestController;
import com.example.springbootshop.rest.exception.receipt.ReceiptDataIntegrityViolationException;
import com.example.springbootshop.rest.exception.receipt.ReceiptNotFoundException;
import com.example.springbootshop.rest.model.entity.Receipt;
import com.example.springbootshop.rest.repository.ReceiptRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptService {

    private static final Logger log = LoggerFactory.getLogger(ReceiptService.class);

    @Autowired
    ReceiptRepository receiptRepository;

    // == Receipt 조회 (By receiptNo) ==
    public Receipt findByReceiptNo(int receiptNo) {
        Receipt receipt = receiptRepository
                .findByReceiptNoAndEnable(receiptNo, true);

        if(receipt == null)
            throw new ReceiptNotFoundException();

        return receipt;
    }

    // == Receipt 저장 ==
    public Receipt save(Receipt receipt) {
        try {
            return receiptRepository.save(receipt);
        } catch (DataIntegrityViolationException e) {
            throw new ReceiptDataIntegrityViolationException();
        }
    }

}
