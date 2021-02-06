package com.homekeeper;

import com.homekeeper.controllers.AuthController;
import com.homekeeper.payload.response.JwtResponse;
import com.homekeeper.security.jwt.TokenUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AuthModuleTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthController authController;

    @Autowired
    TokenUtils tokenUtils;

    @Value("${homekeeper.app.jwtSecret}")
    private String jwtSecret;

    @Value("${homekeeper.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    String username = "user";
    String password = "12345";

    @Test
    @DisplayName("Проверяет успешную подгрузку контроллера из контекста.")
    public void loadControllers() {
        assertThat(authController).isNotNull();
    }

    @Test
    @DisplayName("Проверяет логин с некорректным паролем.")
    public void loginForbiddenTest() throws Exception{
        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"mod\", \"password\": \"123456\" }"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("path").value(""))
                .andExpect(jsonPath("error").value("Unauthorized"))
                .andExpect(jsonPath("message").value("Bad credentials"))
                .andExpect(jsonPath("status").value(401));
    }

    @Test
    @DisplayName("Проверяет аутентификацию пользователя ADMIN.")
    public void testAdminLogin() throws Exception{
        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin\", \"password\": \"12345\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("admin"))
                .andExpect(jsonPath("$.email").value("admin@admin.com"))
                .andExpect((jsonPath("$.roles", Matchers.containsInAnyOrder("ROLE_ADMIN","ROLE_USER"))));
    }

    @Test
    @DisplayName("Проверяет аутентификацию пользователя USER.")
    public void testUserLogin() throws Exception{
        this.mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"user\", \"password\": \"12345\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value("user"))
                .andExpect(jsonPath("$.email").value("user@user.com"))
                .andExpect((jsonPath("$.roles", Matchers.containsInAnyOrder("ROLE_USER"))));
    }

    @Test
    @DisplayName("Проверяет выход без токена.")
    public void logoutFailTest() throws Exception {
        this.mockMvc.perform(get("/api/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"token\": \"\" }"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("path").value(""))
                .andExpect(jsonPath("error").value("Unauthorized"))
                .andExpect(jsonPath("message").value("Full authentication is required to access this resource"))
                .andExpect(jsonPath("status").value(401));

    }

    /**
     * Проверка метода logout, для корректной проверки требует токена с активным статусом и не истекшим сроком
     * @throws Exception
     */
    @Test
    @DisplayName("Проверяет выход с токеном.")
    public void logoutTest() throws Exception {
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(get("/api/auth/logout")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("message").value("You are logout."));
    }

    /**
     * Проверка метода reset, для корректной проверки требует seсretKey
     * @throws Exception
     */
    @Test
    @DisplayName("Проверяет сброс пароля с использованием seсretKey.")
    public void resetTest() throws Exception {
        this.mockMvc.perform(post("/api/auth/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin\", \"secretKey\": \"1234567890\", \"password\": \"12346\" }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").value("admin"))
                .andExpect(jsonPath("password").value("12346"));
    }

    /**
     * Проверка метода reset, для корректной проверки требует seсretKey
     * @throws Exception
     */
    @Test
    @DisplayName("Проверяет сброс пароля с использованием seсretKey, для не существующего пользователя.")
    public void resetNoUserTest() throws Exception {
        this.mockMvc.perform(post("/api/auth/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"bob\", \"secretKey\": \"1234567890\", \"password\": \"12346\" }"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Error: Username does not exist!"));
    }

    /**
     * Проверка метода reset, для корректной проверки требует seсretKey
     * @throws Exception
     */
    @Test
    @DisplayName("Проверяет сброс пароля с использованием некорректного seсretKey, для существующего пользователя.")
    public void resetWrongSecretKeyTest() throws Exception {
        this.mockMvc.perform(post("/api/auth/reset")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin\", \"secretKey\": \"123456789\", \"password\": \"12345\" }"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Error: SecretKey does not valid!"));
    }

}
