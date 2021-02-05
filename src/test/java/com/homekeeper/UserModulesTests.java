package com.homekeeper;

import com.homekeeper.controllers.AuthController;
import com.homekeeper.controllers.UsersController;
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

    @Test
    @DisplayName("Проверяет успешную подгрузку контроллеров из контекста.")
    public void loadControllers() {
        assertThat(usersController).isNotNull();
    }

    @Test
    @DisplayName("Проверяет создание пользователя с ролями ADMIN и USER.")
    public void testCreateAdmin() throws Exception{
        String username = "admin";
        String password = "12345";

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String strToken = jwtUtils.generateJwtToken(authentication);

        tokenUtils.makeToken(username, strToken);

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + strToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"mod\", \"email\": \"mod@mod.com\", \"password\": \"12345\", \"role\": [\"admin\", \"user\"] }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User registered successfully!"));
    }

    @Test
    @DisplayName("Проверяет создание пользователя с ролью USER.")
    public void testCreateUser() throws Exception{
        String username = "admin";
        String password = "12345";

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String strToken = jwtUtils.generateJwtToken(authentication);

        tokenUtils.makeToken(username, strToken);

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + strToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"guest\", \"email\": \"guest@guest.com\", \"password\": \"12345\", \"role\": [\"user\"] }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User registered successfully!"));
    }

    @Test
    @DisplayName("Проверяет создание пользователя с ролью ADMIN")
    public void testCreateAdminAndUser() throws Exception{
        String username = "admin";
        String password = "12345";

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String strToken = jwtUtils.generateJwtToken(authentication);

        tokenUtils.makeToken(username, strToken);

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + strToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin2\", \"email\": \"admin2@admin2.com\", \"password\": \"12345\", \"role\": [\"admin\"] }"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User registered successfully!"));

    }

    @Test
    @DisplayName("Проверяет создание пользователя с существующим userName.")
    public void testCreateUsernameInDb() throws Exception{
        String username = "admin";
        String password = "12345";

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String strToken = jwtUtils.generateJwtToken(authentication);

        tokenUtils.makeToken(username, strToken);

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + strToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin\", \"email\": \"admin2@admin2.com\", \"password\": \"12345\", \"role\": [\"admin\"] }"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Error: Username is already taken!"));

    }

    @Test
    @DisplayName("Проверяет создание пользователя с существующим email.")
    public void testCreateEmailInDb() throws Exception{
        String username = "admin";
        String password = "12345";

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String strToken = jwtUtils.generateJwtToken(authentication);

        tokenUtils.makeToken(username, strToken);

        this.mockMvc.perform(post("/api/auth/users/addUser")
                .header("Authorization", "Bearer " + strToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"userName\": \"admin2\", \"email\": \"admin@admin.com\", \"password\": \"12345\", \"role\": [\"admin\"] }"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("message").value("Error: Email is already in use!"));
    }

//    @Test
//    @DisplayName("Проверяет создание пользователя с не существующей ролью.")
//    public void testCreateRoleNotInDb() throws Exception{
//        String username = "admin";
//        String password = "12345";
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String strToken = jwtUtils.generateJwtToken(authentication);
//
//        tokenUtils.makeToken(username, strToken);
//
//        this.mockMvc.perform(post("/api/auth/users/addUser")
//                .header("Authorization", "Bearer " + strToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{ \"userName\": \"admin2\", \"email\": \"admin2@admin2.com\", \"password\": \"12345\", \"role\": [\"admin\"] }"))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("message").value("Error: Role is not found."));
//    }

//    @Test
//    @DisplayName("Проверяет изменение данных пользователя ADMIN.")
//    public void testChangeAdminData() {
//    }
//
//    @Test
//    @DisplayName("Проверяет изменение данных пользователя USER.")
//    public void testChangeUserData() {
//    }
//
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
//    public void testShowAllUsers() {
//    }
//
//    @Test
//    @DisplayName("Проверяет отображение информации о текущем пользователе.")
//    public void testShowCurrentUserInfo() {
//        // getUserInfo
//    }
}
