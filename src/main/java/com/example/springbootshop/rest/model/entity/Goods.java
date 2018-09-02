package com.example.springbootshop.rest.model.entity;

import com.example.springbootshop.common.model.entity.AduitEntity;
import org.slf4j.Logger;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class Goods {

    @Id
    @Column(name = "goods_no", nullable = false) // == 상품키 ==
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private int goodsNo;

    @Column(name = "title") // == 제목 ==
    private String title;

    @Column(name = " subTitle") // == 부제목 ==
    private String subTitle;

    @Column(name = "thumbnail") // == 대표사진 ==
    private String thumbnail;

    @Column(name = "contents") // == 내용 (블로그글) ==
    private String contents;

    @Column(name = "price") // == 가격 ==
    private int price;

    @Column(name = "stock") // == 재고 ==
    private int stock;

    @Column(name = "active") // == 활성여부 ==
    private boolean active = true;

    @Column(name = "enable", nullable = false) // == 논리삭제 ==
    private boolean enable = true;

    @CreatedDate
    @Column(nullable = false, updatable = false) // == 생성일자 ==
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false) // == 수정일자 ==
    private LocalDateTime updatedAt;

    public int getGoodsNo() {
        return goodsNo;
    }

    public void setGoodsNo(int goodsNo) {
        this.goodsNo = goodsNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void print(Logger log) {
        log.info("[Goods::goodsNo] " + this.getGoodsNo());
        log.info("[Goods::title] " + this.getTitle());
        log.info("[Goods::subTitle] " + this.getSubTitle());
        log.info("[Goods::contents] " + this.getContents());
        log.info("[Goods::thumbnail] " + this.getThumbnail());
        log.info("[Goods::price] " + this.getPrice());
        log.info("[Goods::stock] " + this.getStock());
        log.info("[Goods::active] " + this.isActive());
        log.info("[Goods::enable] " + this.isEnable());
        log.info("[Goods::createdAt] " + this.getCreatedAt());
        log.info("[Goods::updatedAt] " + this.getUpdatedAt());
    }
}
