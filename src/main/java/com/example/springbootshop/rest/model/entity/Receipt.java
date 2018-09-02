package com.example.springbootshop.rest.model.entity;

import com.example.springbootshop.common.model.entity.AduitEntity;
import org.slf4j.Logger;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Receipt extends AduitEntity {

    @Id
    @Column(name = "receipt_no") // == 영수증키 ==
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int receiptNo;

    @Column(name = "purchase_no", nullable = false) // == 구매키 ==
    private int purchaseNo;

    @Column(name = "goods_no", nullable = false) // == 상품키 ==
    private int goodsNo;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="goods_no", nullable=false, insertable=false, updatable=false) // == 상품 ==
    private Goods goods;

    @Column(name = "count", nullable = false) // == 구매수량 ==
    private int count = 1;

    @Column(name = "active") // == 활성여부 ==
    private boolean active = true;

    @Column(name = "enable", nullable = false) // == 논리삭제 ==
    private boolean enable = true;

    // == init constructor required JPA ==
    public Receipt() {}

    public Receipt(int purchaseNo, int goodsNo, int count) {
        this.purchaseNo = purchaseNo;
        this.goodsNo = goodsNo;
        this.count = count;
    }

    public int getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(int receiptNo) {
        this.receiptNo = receiptNo;
    }

    public int getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(int purchaseNo) {
        this.purchaseNo = purchaseNo;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void print(Logger log) {
        log.info("[Receipt::receiptNo] " + this.getReceiptNo());
        log.info("[Receipt::purchaseNo] " + this.getPurchaseNo());
        log.info("[Receipt::goodsNo] " + this.getGoodsNo());
        log.info("[Receipt::count] " + this.getCount());
        log.info("[Receipt::active] " + this.isActive());
        log.info("[Receipt::enable] " + this.isEnable());
    }
}
