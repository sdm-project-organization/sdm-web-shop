package com.example.springbootshop.rest.repository;

import com.example.springbootshop.rest.model.entity.Goods;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends JpaRepository<Goods, String> {

    Goods findByGoodsNoAndEnable(int goodsNo, boolean enable);

    Page<Goods> findAllByEnable(@Param("enable") boolean enable, Pageable pageable);

}
