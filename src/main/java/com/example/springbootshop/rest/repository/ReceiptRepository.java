package com.example.springbootshop.rest.repository;

import com.example.springbootshop.rest.model.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, String> {

    Receipt findByReceiptNoAndEnable(int receiptNo, boolean enable);
}