package com.jinleebriller.jinleespace.dto;

import com.jinleebriller.jinleespace.model.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.jinleebriller.jinleespace.model.User;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String id;

    private String password;

    private String name;

    // 회원가입 시 USER 역할을 부여하기 위해 사용한 코드
    public User toEntity(Set<Role> roles) {
        return User.builder().id(id).password(password).name(name).roles(roles).build();
    }

    /*
    public User toEntity() {
        return User.builder().id(id).password(password).name(name).build();
    }
    */
}
