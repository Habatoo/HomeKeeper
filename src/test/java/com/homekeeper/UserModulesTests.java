package com.homekeeper;

import com.homekeeper.controllers.AuthController;
import com.homekeeper.controllers.UsersController;
import com.homekeeper.payload.response.JwtResponse;
import com.homekeeper.repository.TokenRepository;
import com.homekeeper.repository.UserRepository;
import com.homekeeper.security.jwt.JwtUtils;
import com.homekeeper.security.jwt.TokenUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.HttpServletRequest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.DATE;
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User registered successfully!"));
    }

//    @Test
//    @DisplayName("Проверяет изменение своих данных пользователем с правами ADMIN.")
//    public void testChangeMyAdminData() throws Exception{
//        String id = "1";
//        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
//        tokenUtils.makeToken(username, jwtResponse.getAccessToken());
//
//        this.mockMvc.perform(put("/api/auth/users/" + id)
//                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{ \"userName\": \"admin2\", \"userEmail\": \"admin2@admin2.com\", \"password\": \"12345\"] }"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("message").value("User data was update successfully!"));
//    }

//    @Test
//    @DisplayName("Проверяет изменение не своих данных пользователем с правами ADMIN.")
//    public void testChangeUserData() throws Exception{
//        String id = "2";
//        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
//        tokenUtils.makeToken(username, jwtResponse.getAccessToken());
//
//        this.mockMvc.perform(put("/api/auth/users/" + id)
//                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{ \"userName\": \"user2\", \"userEmail\": \"user2@user2.com\", \"password\": \"12345\"] }"))
//                .andDo(print())
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("message").value("User data was update successfully!"));
//    }

//    @Test
//    @DisplayName("Проверяет изменение своих данных пользователем с правами USER.")
//    public void testChangeMyUserData() throws Exception{
//        String id = "2";
//        JwtResponse jwtResponse = tokenUtils.makeAuth("user", password);
//        tokenUtils.makeToken(username, jwtResponse.getAccessToken());
//
//        this.mockMvc.perform(put("/api/auth/users/" + id)
//                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{ \"userName\": \"user2\", \"userEmail\": \"user2@user2.com\", \"password\": \"12345\"] }"))
//                .andDo(print())
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("message").value("User data was update successfully!"));
//    }

//    @Test
//    @DisplayName("Проверяет изменение не своих данных пользователем с правами USER.")
//    public void testChangeNotMyUserData() throws Exception{
//        String id = "1";
//        JwtResponse jwtResponse = tokenUtils.makeAuth("user", password);
//        tokenUtils.makeToken(username, jwtResponse.getAccessToken());
//
//        this.mockMvc.perform(put("/api/auth/users/" + id)
//                .header("Authorization", "Bearer " + jwtResponse.getAccessToken())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{ \"userName\": \"admin2\", \"userEmail\": \"admin2@admin2.com\", \"password\": \"12345\"] }"))
//                .andDo(print())
//                .andExpect(status().is4xxClientError())
//                .andExpect(jsonPath("message").value("You can edit only yourself data."));
//    }

//    @Test
//    @DisplayName("Проверяет удаление данных пользователя ADMIN.")
//    public void testDeleteAdminData() {
//    }
//
//    @Test
//    @DisplayName("Проверяет удаление данных пользователя USER.")
//    public void testDeleteUserData() {
//    }
//
//    @Test
//    @DisplayName("Проверяет отображение списка всех пользователей.")
//    public void testShowAllUsers() throws Exception{
//        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
//        tokenUtils.makeToken(username, jwtResponse.getAccessToken());
//
//        this.mockMvc.perform(get("/api/auth/users/")
//                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
//                //.contentType(MediaType.APPLICATION_JSON)
//                //.content("{ \"userName\": \"admin2\", \"userEmail\": \"admin2@admin2.com\", \"password\": \"12345\"] }"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect((jsonPath (Matchers. empty())));
//        // [{"balances":[],"roles":[{"id":1,"roleName":"ROLE_ADMIN"},{"id":2,"roleName":"ROLE_USER"}],"userEmail":"admin@admin.com","id":1,"creationDate":"2021-02-06T00:00:00","userName":"admin"},{"balances":[],"roles":[{"id":2,"roleName":"ROLE_USER"}],"userEmail":"user@user.com","id":2,"creationDate":"2021-02-06T00:00:00","userName":"user"}]
//    }

    @Test
    @DisplayName("Проверяет отображение информации о текущем пользователе.")
    public void testShowCurrentUserInfo() throws Exception{
        JwtResponse jwtResponse = tokenUtils.makeAuth(username, password);
        tokenUtils.makeToken(username, jwtResponse.getAccessToken());

        this.mockMvc.perform(get("/api/auth/users/getUserInfo")
                .header("Authorization", "Bearer " + jwtResponse.getAccessToken()))
                //.contentType(MediaType.APPLICATION_JSON)
                //.content("{ \"userName\": \"admin2\", \"userEmail\": \"admin2@admin2.com\", \"password\": \"12345\"] }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").value("admin"))
                .andExpect(jsonPath("userEmail").value("admin@admin.com"))
                .andExpect((jsonPath("balances", Matchers.empty())));
                //.andExpect((jsonPath("roles", Matchers.containsInAnyOrder("ROLE_ADMIN","ROLE_USER"))));
    }
}
