package com.homekeeper;

import com.homekeeper.controllers.AuthController;
import com.homekeeper.controllers.UsersController;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.test.properties")
public class AuthModuleTests {
    @Autowired
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void contextLoads() throws Exception {
        assertThat(authController);
    }

    @Test
    public void loginTest() throws Exception {
        this.mockMvc.perform(post("/api/auth/login").param("admin", "12345"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void loginForbiddenTest() throws Exception {
        this.mockMvc.perform(post("/api/auth/login").param("mod", "12345"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Проверяет аутентификацию пользователя ADMIN.")
    public void testAdminLogin() {
    }

    @Test
    @DisplayName("Проверяет аутентификацию пользователя USER.")
    public void testUserLogin() {
    }

    @Test
    public void logoutTest() throws Exception {
        this.mockMvc.perform(post("/api/auth/logout"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
