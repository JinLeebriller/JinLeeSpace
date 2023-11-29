package com.blog.JinLeeSpace.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// 테이블에 등록일, 수정일을 넣어주는 엔티티

// Auditing을 적용하기 위한 어노테이션
@EntityListeners(value = {AuditingEntityListener.class})
// 부모 클래스를 상속 받는 자식 클래스에 매핑 정보만 제공
@MappedSuperclass
@Getter @Setter
public abstract class BaseTimeEntity {

    // 엔티티가 생성되어 저장될 때 시간을 자동으로 저장
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime regTime;

    // 엔티티의 값을 변경할 때 시간을 자동으로 저장
    @LastModifiedDate
    private LocalDateTime updateTime;

}
