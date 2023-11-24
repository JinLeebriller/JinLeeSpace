package com.blog.JinLeeSpace.repository;

import com.blog.JinLeeSpace.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

// Member 엔티티를 데이터베이스에 저장할 MemberRepository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findById(String id);

}
