package com.homekeeper;

import com.homekeeper.controllers.AuthController;
import com.homekeeper.controllers.UsersController;
import com.homekeeper.models.User;
import com.homekeeper.payload.response.JwtResponse;
import com.homekeeper.repository.TokenRepository;
import com.homekeeper.repository.UserRepository;
import com.homekeeper.security.jwt.JwtUtils;
import com.homekeeper.security.jwt.TokenUtils;
import org.hamcrest.Matchers;
import org.junit.Assert;
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
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserModulesTests {
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

    @Test
    @DisplayName("Проверяет успешную подгрузку контроллеров из контекста.")
    public void loadControllers() {
        assertThat(usersController).isNotNull();
    }

    @Test
    @DisplayName("Проверяет создание пользователя с ролями ADMIN и USER.")
    public void testCreateAdmin() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"mod\", \"email\": \"mod@mod.com\", \"password\": \"12345\", \"role\": [\"admin\", \"user\"] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User registered successfully!"));

        User user = userRepository.findByUserName("mod").get();
        Assert.assertTrue(user.getBalances().toString().contains("0.00"));
    }

    @Test
    @DisplayName("Проверяет создание пользователя с ролью USER.")
    public void testCreateUser() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"guest\", \"email\": \"guest@guest.com\", \"password\": \"12345\", \"role\": [\"user\"] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User registered successfully!"));

        User user = userRepository.findByUserName("guest").get();
        Assert.assertTrue(user.getBalances().toString().contains("0.00"));

    }

    @Test
    @DisplayName("Проверяет создание пользователя с ролью ADMIN")
    public void testCreateAdminAndUser() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin2\", \"email\": \"admin2@admin2.com\", \"password\": \"12345\", \"role\": [\"admin\"] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User registered successfully!"));

        User user = userRepository.findByUserName("admin2").get();
        Assert.assertTrue(user.getBalances().toString().contains("0.00"));
    }

    @Test
    @DisplayName("Проверяет создание пользователя с существующим userName.")
    public void testCreateUsernameInDb() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin\", \"email\": \"admin2@admin2.com\", \"password\": \"12345\", \"role\": [\"admin\"] }"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Error: Username is already taken!"));
    }

    @Test
    @DisplayName("Проверяет создание пользователя с существующим email.")
    public void testCreateEmailInDb() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin2\", \"email\": \"admin@admin.com\", \"password\": \"12345\", \"role\": [\"admin\"] }"))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Error: Email is already in use!"));
    }

    @Test
    @DisplayName("Проверяет создание пользователя с не существующей ролью.")
    public void testCreateRoleNotInDb() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"cat\", \"email\": \"cat@cat.com\", \"password\": \"12345\", \"role\": [\"cat\"] }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User registered successfully!"));

        User user = userRepository.findByUserName("cat").get();
        Assert.assertTrue(user.getBalances().toString().contains("0.00"));
    }

    @Test
    @DisplayName("Проверяет создание пользователя автором без роли ADMIN")
    public void testFailCreateUserWithoutAdminRole() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth("user", password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin2\", \"email\": \"admin2@admin2.com\", \"password\": \"12345\", \"role\": [\"admin\"] }"))
                .andExpect(status().is(403));

        assertEquals("Optional.empty", userRepository.findByUserName("admin2").toString());
    }

    @Test
    @DisplayName("Проверяет изменение своих данных пользователем с правами ADMIN.")
    public void testChangeMyAdminData() throws Exception{
        String id = "1";
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(put("/api/auth/users/" + id)
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin2\", \"userEmail\": \"admin2@admin2.com\", \"password\": \"12345\" }"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("message").value("User data was update successfully!"));

        assertEquals("admin2", userRepository.findByUserName("admin2").get().getUserName());
        assertEquals("admin2@admin2.com", userRepository.findByUserName("admin2").get().getUserEmail());

    }

    @Test
    @DisplayName("Проверяет изменение не своих данных пользователем с правами ADMIN.")
    public void testChangeUserData() throws Exception{
        String id = "2";
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(put("/api/auth/users/" + id)
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"user2\", \"userEmail\": \"user2@user2.com\", \"password\": \"12345\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User data was update successfully!"));

        assertEquals("user2", userRepository.findByUserName("user2").get().getUserName());
        assertEquals("user2@user2.com", userRepository.findByUserName("user2").get().getUserEmail());
    }

    @Test
    @DisplayName("Проверяет изменение своих данных пользователем с правами USER.")
    public void testChangeMyUserData() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth("user", password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(put("/api/auth/users/2")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"user2\", \"userEmail\": \"user2@user2.com\", \"password\": \"12345\" }"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("message").value("User data was update successfully!"));

        assertEquals("user2", userRepository.findByUserName("user2").get().getUserName());
        assertEquals("user2@user2.com", userRepository.findByUserName("user2").get().getUserEmail());

    }

    @Test
    @DisplayName("Проверяет изменение не своих данных пользователем с правами USER.")
    public void testChangeNotMyUserData() throws Exception{
        String id = "1";
        JwtResponse jwtResponse = tokenUtils.makeAuth("user", password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(put("/api/auth/users/" + id)
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin2\", \"userEmail\": \"admin2@admin2.com\", \"password\": \"12345\" }"))
                .andExpect(status().is(400));
                //.andExpect(jsonPath("message").value("You can edit only yourself data."));
    }

    @Test
    @DisplayName("Проверяет удаление пользователя автором с ролью ADMIN.")
    public void testDeleteUserByAdmin() throws Exception{
        String id = "2";
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(delete("/api/auth/users/" + id)
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User was deleted successfully!"));
    }

    @Test
    @DisplayName("Проверяет удаление пользователя автором с ролью USER.")
    public void testDeleteUserByUser() throws Exception{
        String id = "1";
        JwtResponse jwtResponse = tokenUtils.makeAuth("user", password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(delete("/api/auth/users/" + id)
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                .andExpect(status().is(403));
    }

    @Test
    @DisplayName("Проверяет отображение списка всех пользователей.")
    public void testShowAllUsers() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());
        Date date = new Date();
        String resp = "[{\"balances\":[],\"roles\":[{\"id\":1,\"roleName\":\"ROLE_ADMIN\"},{\"id\":2,\"roleName\":\"ROLE_USER\"}],\"userEmail\":\"admin@admin.com\",\"id\":1,\"creationDate\":\"" + date + "\",\"userName\":\"admin\"},{\"balances\":[],\"roles\":[{\"id\":2,\"roleName\":\"ROLE_USER\"}],\"userEmail\":\"user@user.com\",\"id\":2,\"creationDate\":\"" + date + "\",\"userName\":\"user\"}]";

        this.mockMvc.perform(get("/api/auth/users/")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse().getContentAsString().equals(resp);
    }

    @Test
    @DisplayName("Проверяет отображение информации о текущем пользователе.")
    public void testShowCurrentUserInfo() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(get("/api/auth/users/getUserInfo")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").value("admin"))
                .andExpect(jsonPath("userEmail").value("admin@admin.com"))
                .andExpect((jsonPath("balances", Matchers.empty())));
    }

    @Test
    @DisplayName("Проверяет срок действия токенов на валидном токене.")
    public void testTokensDataCheck() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(delete("/api/auth/users/tokens")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("All tokens have valid expiry date!"));
    }

    @Test
    @DisplayName("Проверяет срок действия токенов и очистку базу токенов.")
    public void testTokensDataClean() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        // Create old token
        Assert.assertEquals(1, tokenRepository.findAll().size());
        tokenUtils.makeOldToken(username, password);
        Assert.assertEquals(2, tokenRepository.findAll().size());

        this.mockMvc.perform(delete("/api/auth/users/tokens")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Tokens with expiry date was deleted successfully!"));

        Assert.assertEquals(1, tokenRepository.findAll().size());
    }
}
