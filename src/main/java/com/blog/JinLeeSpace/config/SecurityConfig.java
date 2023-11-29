package com.blog.JinLeeSpace.config;

import com.blog.JinLeeSpace.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    MemberService memberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/css/**", "/js/**", "/img/**", "/webjars/**", "/favicon.ico").permitAll()
                .requestMatchers("/", "/member/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        )
        .formLogin(login -> login
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .usernameParameter("id")
                .failureUrl("/member/login/error")
        )
        .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
        )
        .exceptionHandling(handler -> handler
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        );

        http.rememberMe(rememberMe -> rememberMe
                .tokenValiditySeconds(60 * 60 * 24 * 14)
                .userDetailsService(memberService)
                .rememberMeParameter("rememberMe"));

        return http.build();
    }

    /*
        비밀번호를 데이터베이스에 그대로 저장했을 경우,
        데이터베이스가 해킹당하면 고객의 회원 정보가 그대로 노출된다.

        이를 해결하기 위해 BCryptPasswordEncoder의 해시 함수를 이용하여
        비밀번호를 암호화하여 저장한다.
    */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
