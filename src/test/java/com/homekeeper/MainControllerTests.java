package com.homekeeper;

import com.homekeeper.controllers.MainController;
import com.homekeeper.payload.response.JwtResponse;
import com.homekeeper.repository.TokenRepository;
import com.homekeeper.security.jwt.JwtUtils;
import com.homekeeper.security.jwt.TokenUtils;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MainController mainController;

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

    @Value("${homekeeper.app.jwtSecret}")
    private String jwtSecret;

    @Value("${homekeeper.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    String username = "admin";
    String password = "12345";

    @Test
    @DisplayName("Проверяет успешную подгрузку контроллеров из контекста.")
    public void loadControllers() {
        assertThat(mainController).isNotNull();
    }

    @Test
    @DisplayName("Проверяет добавление в базу данных за текущий месяц.")
    public void testsAddMonthData () throws Exception {
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(post("/api/auth/addTariff")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"waterColdRate\": \"1\", \"waterHotRate\": \"2\", \"electricityRate\": \"3\", \"internetRate\": \"4\", \"waterOutRate\": \"6\", \"rentRate\": \"5\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Tariff added successfully!"));
    }


}
