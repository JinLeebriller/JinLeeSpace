package com.blog.JinLeeSpace.service;

import com.blog.JinLeeSpace.entity.Member;
import com.blog.JinLeeSpace.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
// MemberService가 UserDetailsService를 구현(UserDetailsService : 데이터베이스에서 회원 정보를 가져오는 역할)
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // Member 데이터베이스에 멤버 저장
    public Member saveMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    // 이미 가입된 회원의 경우 IllegalStateException 예외 발생
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findById(member.getId());
        if(findMember.isPresent()) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    /*
        데이터베이스에서 회원 정보를 가져오는 UserDetailsService 인터페이스는
        회원 정보를 조회하여 사용자의 정보와 권한을 갖는 UserDetails 인터페이스를 반환하는
        loadUserByUsername() 메서드를 오버라이딩 해야한다.

        스프링 시큐리티에서 제공하는 User 클래스는 UserDetails 인터페이스를 구현하는 클래스이다.
    */
    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(id));

        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}
