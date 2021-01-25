package com.homekeeper;

import com.homekeeper.controllers.UsersController;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource("/application.test.properties")
public class UserModulesTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsersController usersController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(usersController).isNotNull();
    }

    @Test
    @DisplayName("Проверяет.")
    void userPageData()  throws Exception {
        // usersController.userList();
        this.mockMvc.perform(get("/api/auth/users/"))
                .andDo(print())
                .andExpect(authenticated());
        // System.out.println(usersController.userList());
    }

    @Test
    @DisplayName("Проверяет создание пользователя с ролью ADMIN.")
    void testCreateAdmin() {
        // usersController.userList();
        System.out.println(usersController.userList());
    }

    @Test
    @DisplayName("Проверяет создание пользователя с ролью USER.")
    void testCreateUser() {
    }

    @Test
    @DisplayName("Проверяет создание пользователя с ролями ADMIN и USER.")
    void testCreateAdminAndUser() {
    }

    @Test
    @DisplayName("Проверяет создание пользователя с существующим userName.")
    void testCreateUsernameInDb() {
    }

    @Test
    @DisplayName("Проверяет создание пользователя с существующим email.")
    void testCreateEmailInDb() {
    }

    @Test
    @DisplayName("Проверяет создание пользователя с не существующей ролью.")
    void testCreateRoleNotInDb() {
    }

    @Test
    @DisplayName("Проверяет изменение данных пользователя ADMIN.")
    void testChangeAdminData() {
    }

    @Test
    @DisplayName("Проверяет изменение данных пользователя USER.")
    void testChangeUserData() {
    }

    @Test
    @DisplayName("Проверяет удаление данных пользователя ADMIN.")
    void testDeleteAdminData() {
    }

    @Test
    @DisplayName("Проверяет удаление данных пользователя USER.")
    void testDeleteUserData() {
    }

    @Test
    @DisplayName("Проверяет отображение списка всех пользователей.")
    void testShowAllUsers() {
    }

    @Test
    @DisplayName("Проверяет отображение информации о текущем пользователе.")
    void testShowCurrentUserInfo() {
        // getUserInfo
    }
}
