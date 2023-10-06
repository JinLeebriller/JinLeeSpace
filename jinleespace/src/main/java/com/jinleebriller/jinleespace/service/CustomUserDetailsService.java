package com.jinleebriller.jinleespace.service;

import com.jinleebriller.jinleespace.dao.UserRepository;
import org.springframework.security.core.userdetails.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
/*
UserDetailsService : 스프링 security에서 사용자의 정보를 가져오기 위한 인터페이스
주로 사용자 인증에 활용되며 이 인터페이스를 구현하는 클래스는 사용자의 정보를 제공하는 메서드를 구현해야 한다.
*/
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    // 읽기 전용 트랜잭션은 데이터베이스에서 데이터를 읽기만 하고 수정하지 않는 경우 사용. 읽기 작업이 최적화 되어 성능 향상
    @Transactional(readOnly = true)
    // loadUserByUsername : 사용자 이름(일반적으로 아이디)를 받아와서 UserDetails 객체를 반환
    // UserDetails : 스프링 security에서 제공하는 사용자 모델의 인터페이스. 사용자의 ID, Password, 권한 등을 나타내는 역할
    public UserDetails loadUserByUsername(String id) {

        // GrantedAuthority : 사용자의 권한을 나타내는 인터페이스
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        com.jinleebriller.jinleespace.model.User user = userRepository.findOneById(id);

        if(user != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority("USER")); // USER라는 역할을 넣어준다.
            // User : 스프링 Security에서 제공하는 기본 사용자 모델(사용자의 ID, Password, 권한 등을 관리)
            return new User(user.getId(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("can not find User : " + id);
        }

    }

}
