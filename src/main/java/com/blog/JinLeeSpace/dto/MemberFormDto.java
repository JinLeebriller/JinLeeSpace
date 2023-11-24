package com.blog.JinLeeSpace.dto;

import lombok.Getter;
import lombok.Setter;

// 회원가입 화면으로부터 넘어오는 가입정보를 담을 dto
@Getter @Setter
public class MemberFormDto {

    // 아이디
    private String id;

    // 비밀번호
    private String password;

    // 닉네임
    private String nickName;

    // 이메일
    private String email;

    // 주소
    private String address;

}
