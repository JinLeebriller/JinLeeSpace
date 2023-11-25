package com.blog.JinLeeSpace.controller;

import com.blog.JinLeeSpace.dto.MemberFormDto;
import com.blog.JinLeeSpace.entity.Member;
import com.blog.JinLeeSpace.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    /*
        MockMvc 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체;
        웹 브라우저에서 요청을 하는 것처럼 테스트할 수 있다.
    */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 로그인 테스트를 진행하기 위해 로그인 전 회원을 등록하는 메서드
    public Member createMember(String id, String password) {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setId(id);
        memberFormDto.setPassword(password);
        memberFormDto.setNickName("에렌예거");
        memberFormDto.setEmail("test@test.com");
        memberFormDto.setAddress("서울시 마포구 합정동");

        Member member = Member.createMember(memberFormDto, passwordEncoder);

        return memberService.saveMember(member);
    }

    // 로그인 성공 테스트
    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String id = "id001";
        String password = "12345678";
        this.createMember(id, password);

        mockMvc.perform(formLogin().userParameter("id")
                        .loginProcessingUrl("/member/login")
                        .user(id).password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    // 로그인 실패 테스트
    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String id = "id001";
        String password = "1234678";
        this.createMember(id, password);

        mockMvc.perform(formLogin().userParameter("id")
               .loginProcessingUrl("/member/login")
               .user(id).password("abcdefgh"))
               .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}