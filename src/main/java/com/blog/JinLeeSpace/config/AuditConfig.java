package com.blog.JinLeeSpace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Auditing 기능(엔티티가 저장 또는 수정될 때 자동으로 등록일, 수정일, 등록자, 수정자를 입력)을 사용하기 위한 Config 클래스
@Configuration
// JPA의 Auditing 기능을 활성화
@EnableJpaAuditing
public class AuditConfig {

    // 등록자와 수정자를 처리해주는 AuditorAware을 빈으로 등록
    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

}
