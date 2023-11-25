package com.blog.JinLeeSpace.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    // 로그인된 사용자의 role이 ADMIN이라면, /admin/admin/test 페이지에 접근 가능한지 테스트
    @Test
    @DisplayName("Admin 페이지 Admin 회원 접근 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void adminTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/admin/test"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    // 로그인된 사용자의 role이 USER이라면, /admin/admin/test 페이지에 접근 가능한지 테스트
    @Test
    @DisplayName("Admin 페이지 일반 회원 접근 테스트")
    @WithMockUser(username = "user", roles = "USER")
    public void notAdminTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/admin/test"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}