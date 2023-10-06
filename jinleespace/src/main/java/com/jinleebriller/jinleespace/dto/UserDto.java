package com.jinleebriller.jinleespace.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.jinleebriller.jinleespace.model.User;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

    private String id;

    private String password;

    private String name;

    public User toEntity() {
        return User.builder().id(id).password(password).name(name).build();
    }

}
