package com.homekeeper;

import com.homekeeper.controllers.AuthController;
import com.homekeeper.controllers.UserBalanceController;
import com.homekeeper.controllers.UsersController;
import com.homekeeper.models.UserBalance;
import com.homekeeper.payload.response.JwtResponse;
import com.homekeeper.repository.TokenRepository;
import com.homekeeper.repository.UserRepository;
import com.homekeeper.security.jwt.JwtUtils;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserBalanceModuleTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersController usersController;

    @Autowired
    private AuthController authController;

    @Autowired
    HttpServletRequest request;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    TokenUtils tokenUtils;

    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    UserRepository userRepository;

    @Value("${homekeeper.app.jwtSecret}")
    private String jwtSecret;

    @Value("${homekeeper.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    String username = "admin";
    String password = "12345";

    @Autowired
    UserBalanceController userBalanceController;


    @Test
    @DisplayName("Проверяет успешную подгрузку контроллера из контекста.")
    public void loadControllers() {
        assertThat(userBalanceController).isNotNull();
    }

    @Test
    @DisplayName("Проверяет добавление средств на баланс пользователя.")
    public void testAddFundsToBalance() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(post("/api/auth/balances/addFundsToBalance")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"balanceSumOfBalance\": \"199.99\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Balance added successfully!"));
    }

    @Test
    @DisplayName("Проверяет корректировку средств на балансе пользователя, ответ при пустой базе, добавление средств и успешная корректировка")
    public void testChangeBalance() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(put("/api/auth/balances/changeBalance")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"balanceSumOfBalance\": \"199.99\" }"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Database is empty!"));

        this.mockMvc.perform(post("/api/auth/balances/addFundsToBalance")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"balanceSumOfBalance\": \"199.99\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Balance added successfully!"));

        this.mockMvc.perform(put("/api/auth/balances/changeBalance")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"balanceSumOfBalance\": \"100.00\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Balance data change successful!"));
    }

    @Test
    @DisplayName("Отображает список пополнений баланса от пользователя.")
    public void testShowBalance() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(get("/api/auth/balances")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                .andExpect(status().is(400))
                .andExpect(jsonPath("message").value("Database is empty!"));

        this.mockMvc.perform(post("/api/auth/balances/addFundsToBalance")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"balanceSumOfBalance\": \"199.99\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Balance added successfully!"));

        this.mockMvc.perform(get("/api/auth/balances")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                .andExpect(status().is(200))
                .andExpect(jsonPath("balanceSumOfBalance").value("199.99"));

    }
}
