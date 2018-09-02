package com.example.springbootshop.rest.repository;

import com.example.springbootshop.rest.model.entity.Basket;
import com.example.springbootshop.rest.model.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends JpaRepository<Basket, String> {

    long countByUserNoAndEnable(int userNo, boolean enable);

    Basket findByBasketNoAndEnable(int basketNo, boolean enable);

    Basket findByUserNoAndGoodsNoAndEnable(int userNo, int goodsNo, boolean enable);

    Page<Basket> findAllByEnable(@Param("enable") boolean enable, Pageable pageable);

    Page<Basket> findAllByUserNoAndEnable(@Param("user_no") int userNo, @Param("enable") boolean enable, Pageable pageable);

    List<Basket> findAllByUserNoAndEnable(@Param("user_no") int userNo, @Param("enable") boolean enable);

}