package com.example.springbootshop.common.model.entity;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class ExceptionLog {

    @Id
    @Column(name = "exception_no") // == 에러키 ==
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int exceptionNo;

    @Column(name = "location") // == 발생장소 ==
    private String location;

    @Column(name = "clazz") // == 클래스 ==
    private String clazz;

    @Column(name = "message") // == 메세지 ==
    private String message;

    @CreatedDate // == 생성일자 ==
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // == 수정일자 ==
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
