package com.example.springbootshop.rest.model.entity;

import com.example.springbootshop.common.model.entity.AduitEntity;
import com.example.springbootshop.user.model.entity.ShopUser;
import org.slf4j.Logger;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class Basket extends AduitEntity {

    @Id
    @Column(name = "basket_no") // == 장바구니키 ==
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int basketNo;

    @Column(name = "user_no") // == 유저키 ==
    private int userNo;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="user_no", nullable=false, insertable=false, updatable=false) // == 유저 ==
    private ShopUser user;

    @Column(name = "goods_no") // == 상품키 ==
    private int goodsNo;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="goods_no", nullable=false, insertable=false, updatable=false) // == 상품 ==
    private Goods goods;

    @Column(name = "count") // == 수량 ==
    private int count;

    @Column(name = "active") // == 활성여부 ==
    private boolean active = true;

    @Column(name = "enable") // == 논리삭제 ==
    private boolean enable = true;

    @CreatedDate
    @Column(nullable = false, updatable = false) // == 생성일자 ==
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false) // == 수정일자 ==
    private LocalDateTime updatedAt;

    public int getBasketNo() {
        return basketNo;
    }

    public void setBasketNo(int basketNo) {
        this.basketNo = basketNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public int getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(int goodsNo) {
        this.goodsNo = goodsNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public ShopUser getUser() {
        return user;
    }

    public void setUser(ShopUser user) {
        this.user = user;
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void print(Logger log) {
        log.info("[Basket::basketNo] " + this.getBasketNo());
        log.info("[Basket::userNo] " + this.getUserNo());
        log.info("[Basket::goodsNo] " + this.getGoodsNo());
        log.info("[Basket::count] " + this.getCount());
        log.info("[Basket::active] " + this.isActive());
        log.info("[Basket::enable] " + this.isEnable());
    }
}
