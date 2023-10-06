package com.jinleebriller.jinleespace.service;

import com.jinleebriller.jinleespace.dao.RoleRepository;
import com.jinleebriller.jinleespace.dao.UserRepository;
import com.jinleebriller.jinleespace.dto.UserDto;
import com.jinleebriller.jinleespace.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원가입 시 USER 역할을 부여하기 위해 사용한 코드
    private final RoleRepository roleRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void insert(UserDto userDto) {
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        // 회원가입 시 USER 역할을 부여하기 위해 사용한 코드
        Set<Role> roleSet = new HashSet<Role>();
        roleSet.add(roleRepository.findOneById(2L)); // id : 2 ROLE_USER 역할
        userRepository.save(userDto.toEntity(roleSet));

        // userRepository.save(userDto.toEntity());
    }

}
