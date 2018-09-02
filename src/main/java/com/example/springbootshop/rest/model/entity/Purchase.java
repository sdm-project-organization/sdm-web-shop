package com.example.springbootshop.rest.model.entity;

import com.example.springbootshop.common.model.entity.AduitEntity;
import com.example.springbootshop.user.model.entity.ShopUser;
import org.slf4j.Logger;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class Purchase extends AduitEntity {

    @Id
    @Column(name = "purchase_no") // == 구매키 ==
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int purchaseNo;

    @Column(name = "user_no") // == 유저키 ==
    private int userNo;

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="user_no", nullable=false, insertable=false, updatable=false) // == 유저 ==
    private ShopUser user;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="purchase_no", nullable=false, insertable=false, updatable=false) // == 영수증 ==
    private Collection<Receipt> receipts;

    @Column(name = "tel") // == 전화번호 ==
    private String tel;

    @Column(name = "address") // == 주소지 ==
    private String address;

    @Column(name = "payment") // == 결제완료 ==
    private boolean payment = false;

    @Column(name = "delivery") // == 배송여부 ==
    private boolean delivery = false;

    @Column(name = "active") // == 활성상태 ==
    private boolean active = true;

    @Column(name = "enable") // == 논리삭제 ==
    private boolean enable = true;

    @CreatedDate
    @Column(nullable = false, updatable = false) // == 생성일자 ==
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false) // == 수정일자 ==
    private LocalDateTime updatedAt;

    public int getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(int purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isDelivery() {
        return delivery;
    }

    public void setDelivery(boolean delivery) {
        this.delivery = delivery;
    }

    public boolean isPayment() {
        return payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public ShopUser getUser() {
        return user;
    }

    public void setUser(ShopUser user) {
        this.user = user;
    }

    public Collection<Receipt> getReceipts() {
        return receipts;
    }

    public void setReceipts(Collection<Receipt> receipts) {
        this.receipts = receipts;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // == Purchase 테스트 ==
    public void print(Logger log) {
        log.info("[Purchase::purchaseNo] " + this.getPurchaseNo());
        log.info("[Purchase::userNo] " + this.getUserNo());
        log.info("[Purchase::tel] " + this.getTel());
        log.info("[Purchase::address] " + this.getAddress());
        log.info("[Purchase::payment] " + this.isPayment());
        log.info("[Purchase::delivery] " + this.isDelivery());
        log.info("[Purchase::active] " + this.isActive());
        log.info("[Purchase::enable] " + this.isEnable());
        log.info("[Purchase::createdAt] " + this.getCreatedAt());
        log.info("[Purchase::updatedAt] " + this.getUpdatedAt());
    }
}
