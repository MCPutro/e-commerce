//package com.ecommerce.auth.model.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.EntityListeners;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.Instant;
//
//@Setter
//@Getter
//@EntityListeners(AuditingEntityListener.class)
//public class BaseEntity {
//
//    @CreatedDate
//    @Column(name = "created_at", updatable = false)
//    private Instant createdAt;
//
//    @CreatedBy
//    @Column(name = "created_by", updatable = false, nullable = false)
//    private String createdBy;
//
//    @LastModifiedDate
//    @Column(name = "updated_date")
//    private Instant updatedDate;
//
//    @LastModifiedBy
//    @Column(name = "updated_by", insertable = false)
//    private String updatedBy;
//}
