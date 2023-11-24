package com.blog.JinLeeSpace.entity;

import com.blog.JinLeeSpace.constant.Role;
import com.blog.JinLeeSpace.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

// 회원 정보를 저장하는 Member 엔티티
@Entity
@Table(name = "member")
@Getter @Setter
@ToString
public class Member {

    // 멤버별 고유번호
    @Id
    @Column(name = "member_idNumber")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String identificationNumber;

    // 아이디
    /*
        회원은 아이디를 통해 유일하게 구분해야 하기 때문에,
        동일한 값이 데이터베이스에 들어올 수 없도록 unique 속성 지정
    */
    @Column(unique = true)
    private String id;

    // 비밀번호
    private String password;

    // 닉네임
    private String nickName;

    // 이메일
    private String email;

    // 주소
    private String address;

    // 일반 유저인지, 아니면 관리자인지 구분할 수 있는 역할
    @Enumerated(EnumType.STRING)
    private Role role;

    /*
        Member 엔티티를 생성하는 메서드
        스프링 시큐리티 설정 클래스에 등록한 BCryptPasswordEncoder Bean을 파라미터로 받아서 비밀번호를 암호화
    */
    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setId(memberFormDto.getId());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());
        member.setRole(Role.USER);

        return member;
    }

}
