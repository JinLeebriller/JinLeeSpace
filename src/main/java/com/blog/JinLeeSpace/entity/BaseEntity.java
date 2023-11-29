package com.blog.JinLeeSpace.entity;

// 테이블에 등록일, 수정일, 등록자, 수정자를 넣어주는 엔티티

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
public abstract class BaseEntity extends BaseTimeEntity {

    // 엔티티가 생성되어 저장될 때 등록자를 자동으로 저장
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    // 엔티티의 값을 변경할 때 수정자를 자동으로 저장
    @LastModifiedBy
    private String modifiedBy;

}
