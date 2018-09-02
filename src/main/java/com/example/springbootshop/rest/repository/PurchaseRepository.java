package com.example.springbootshop.rest.repository;

import com.example.springbootshop.rest.model.entity.Basket;
import com.example.springbootshop.rest.model.entity.Goods;
import com.example.springbootshop.rest.model.entity.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, String> {

    long countByUserNoAndEnable(int userNo, boolean enable);

    Purchase findByPurchaseNoAndEnable(int purchaseNo, boolean enable);

    Page<Purchase> findAllByEnable(@Param("enable") boolean enable, Pageable pageable);

    Page<Purchase> findAllByUserNoAndEnable(@Param("user_no") int userNo, @Param("enable") boolean enable, Pageable pageable);

}
