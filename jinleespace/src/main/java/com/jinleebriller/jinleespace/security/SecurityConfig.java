package com.jinleebriller.jinleespace.security;

import com.jinleebriller.jinleespace.enums.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    // BCryptPasswordEncoder : 비밀번호를 안전하게 해시화하는 데 사용되는 Spring Security의 클래스
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/home", "/signUp").permitAll() // 설정한 리소스의 접근을 인증절차 없이 허용
                    .antMatchers("/system").hasRole(UserRole.SYSTEM.toString())  // SYSTEM 역할을 가지고 있어야 접근 허용
                    .anyRequest().authenticated()  // 그 외 모든 리소스 인증 필요
                    .and()
                .formLogin()
                    .permitAll()
                    .loginPage("/login") // 기본 로그인 페이지
                    .and()
                .logout()
                    .permitAll()
                    // .logoutUrl("/logout") // 로그아웃 URL (기본값 : /logout)
                    // .logoutSuccessUrl("/login?logout") // 로그아웃 성공 URL (기본값 : /login?logout)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 주소창에 요청해도 포스트로 인식하여 로그아웃
                    .deleteCookies("JSESSIONID") // 로그아웃 시 JSESSIONID 제거
                    .invalidateHttpSession(true) // 로그아웃 시 세션 종료
                    .clearAuthentication(true) // 로그아웃 시 권한 제거
                    .and()
                .exceptionHandling()  // 시큐리티 구성에서 예외 처리 관련 설정을 할 때 사용
                    .accessDeniedPage("/accessDenied");  // 접근 권한이 없는 사용자를 리다이렉트할 페이지 설정
        return http.build();
    }

    @Autowired
    // 전역적인 사용자 인증 관리자를 설정. 이 설정은 애플리케이션 전반에 걸쳐 사용되는 기본적인 사용자 정보를 정의하고, 사용자가 로그인할 때 이 정보를 이용하여 인증을 수행
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    /*
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
    */

}
