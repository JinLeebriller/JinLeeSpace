package com.blog.JinLeeSpace.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 업로드한 파일을 읽어올 경로 설정
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    // application.properties에 설정한 "uploadPath" 프로퍼티 값을 읽어온다.
    @Value("${uploadath}")
    String uploadPath;

    // 로컬 컴퓨터에 업로드한 파일을 찾을 위치를 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 웹 브라우저에 입력하는 url에 /images로 시작하는 경우 uploadPath에 설정한 폴더를 기준으로 파일을 읽어도록 설정
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}
