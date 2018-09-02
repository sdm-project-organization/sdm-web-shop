package com.example.springbootshop.user.model.entity;

import com.example.springbootshop.common.model.entity.AduitEntity;
import org.slf4j.Logger;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class ShopUser extends AduitEntity {

    @Id
    @Column(name = "user_no") // == 유저키 ==
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userNo;

    @Column(name = "user_id", nullable = false) // == 아이디 (이메일) ==
    private String userId;

    @Column(name = "user_password", nullable = false) // == 패스워드  ==
    private String password;

    @Column(name= "authority", nullable = false)
    private String authority;

    @Column(name = "active", nullable = false) // == 활성여부 ==
    private boolean active= true;

    @Column(name = "enable", nullable = false) // == 논리삭제 ==
    private boolean enable = true;

    @CreatedDate
    @Column(nullable = false, updatable = false) // == 생성일자 ==
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false) // == 수정일자 ==
    private LocalDateTime updatedAt;

    public ShopUser(){}

    public ShopUser(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public int getUserNo() {
        return userNo;
    }

    public void setUserNo(int userNo) {
        this.userNo = userNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
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

    }
}
