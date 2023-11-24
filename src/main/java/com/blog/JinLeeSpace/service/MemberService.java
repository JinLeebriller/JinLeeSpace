package com.blog.JinLeeSpace.service;

import com.blog.JinLeeSpace.entity.Member;
import com.blog.JinLeeSpace.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // Member 데이터베이스에 멤버 저장
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 이미 가입된 회원의 경우 IllegalStateException 예외 발생
    private void validateDuplicateMember(Member member) {
        Member findMember = memberRepository.findById(member.getId());
        if(findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

}
