package com.jinleebriller.jinleespace.security;

import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/home").permitAll() // 설정한 리소스의 접근을 인증절차 없이 허용
                    .anyRequest().authenticated()  // 그 외 모든 리소스 인증 필요
                    .and()
                .formLogin()
                    .loginPage("/login") // 기본 로그인 페이지
                    .permitAll()
                    .and()
                .logout()
                    .permitAll();
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService()  {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                    .username("user")
                    .password("password")
                    .roles("USER")
                    .build();
        return new InMemoryUserDetailsManager(user);  // 메모리에 사용자 정보를 담는다.
    }

}
