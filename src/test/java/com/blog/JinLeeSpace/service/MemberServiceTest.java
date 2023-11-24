package com.blog.JinLeeSpace.service;

import com.blog.JinLeeSpace.dto.MemberFormDto;
import com.blog.JinLeeSpace.entity.Member;
import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    // 회원 정보를 입력한 Member 엔티티를 만드는 메서드
    public Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setId("test01");
        memberFormDto.setPassword("12345");
        memberFormDto.setNickName("테스트아이디01");
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setAddress("서울시 마포구 합정동");
        return Member.createMember(memberFormDto, passwordEncoder);
    }

    // 회원 데이터베이스에 저장하려고 요청했던 값과 실제 저장된 데이터를 비교
    @Test
    @DisplayName("회원가입 테스트")
    public void saveMemberTest() {
        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        assertEquals(member.getId(), savedMember.getId());
        assertEquals(member.getPassword(), savedMember.getPassword());
        assertEquals(member.getNickName(), savedMember.getNickName());
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getAddress(), savedMember.getAddress());
        assertEquals(member.getRole(), savedMember.getRole());
    }

    // 중복 아이디로 회원 가입을 시도할 경우 예외처리가 잘 되는지 테스트
    @Test
    @DisplayName("중복 회원 가입 테스트")
    public void saveDuplicateMemberTest() {
        Member member1 = createMember();
        Member member2 = createMember();
        memberService.saveMember(member1);

        Throwable e = assertThrows(IllegalStateException.class, () -> {memberService.saveMember(member2);});

        assertEquals("이미 가입된 회원입니다.", e.getMessage());
    }

}