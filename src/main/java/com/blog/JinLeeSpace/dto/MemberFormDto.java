package com.blog.JinLeeSpace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

// 회원가입 화면으로부터 넘어오는 가입정보를 담을 dto
@Getter @Setter
public class MemberFormDto {

    // 아이디
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String id;

    // 비밀번호
    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=0, max=16, message="비밀번호는 0자 이상, 16자 이하로 입력해주세요.")
    private String password;

    // 닉네임
    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    private String nickName;

    // 이메일
    @NotEmpty(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식으로 입력해주세요.")
    private String email;

    // 주소
    @NotEmpty(message = "주소는 필수 입력 값입니다.")
    private String address;

}
