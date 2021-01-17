package com.homekeeper;

import com.homekeeper.controllers.UsersController;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserModulesTests {
    @Autowired
    private UsersController usersController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(usersController).isNotNull();
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
    @DisplayName("Проверяет аутентификацию пользователя ADMIN.")
    void testAdminLogin() {
    }

    @Test
    @DisplayName("Проверяет аутентификацию пользователя USER.")
    void testUserLogin() {
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
