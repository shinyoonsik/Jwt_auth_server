package com.jwt.auth_server.controller;

import com.jwt.auth_server.config.SecurityConfig;
import com.jwt.auth_server.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
class IndexControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberRepository memberRepository;

    @MockBean
    AuthenticationManager authenticationManager;


    @Test
    @DisplayName("/index 테스트")
    @WithAnonymousUser
    void 테스트_index_controller() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/index"))
                .andExpect(status().isOk())
                .andExpect(content().string("<h1>hello</h1>"))
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo("<h1>hello</h1>");
    }


    @Test
    @DisplayName("/user 테스트")
    @WithMockUser(username = "user")
    void 테스트_user() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user"))
                .andExpect(status().isOk())
                .andExpect(content().string("<h1>USER</h1>"))
                .andDo(print());
    }


    @Test
    @DisplayName("/admin 테스트")
    @WithMockUser(username = "admin", roles = {"ADMIN"}) // @WithMockUser(username = "admin", roles = {"ADMIN"})을 사용하면 admin에 ROLE_ADMIN이라는 권한(ROLE_접두사가 붙음)이 부여됨
    void 테스트_admin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/admin"))
                .andExpect(status().isOk())
                .andExpect(content().string("<h1>ADMIN</h1>"))
                .andDo(print());
    }

    @Test
    @DisplayName("/super-admin")
    @WithMockUser
    void 테스트_superAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/super-admin"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}