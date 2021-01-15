package com.homekeeper.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Модель пользователя. Записывается в БД в таблицу с имененм users.
 * @version 0.013
 * @author habatoo
 *
 * @param "id" - primary key таблицы users.
 * @param "userName" - имя пользователя - предпоалагается строковоя переменная Имя + Фамилия.
 * @param "password" - пароль, в БД хранится в виде хешированном виде.
 * @param "userEmail" - email пользователя.
 * @param "creationDate" - дата создания пользователя.
 *
 * @param "roles" - email пользователя, связи через таблицу user-roles
 * @see Role (роли пользователя).
 *
 * @param "balances" - email пользователя, связи через таблицу user_balances
 * @see UserBalance (платежи внесенные пользователем).
 */
@Entity
@Table(name = "users")
@ToString(of = {"id", "firstName", "lastName", "userEmail", "creationDate"})
@EqualsAndHashCode(of = {"id"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private String password;
    private String userEmail;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "user_balances",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "balance_id"))
    private Set<UserBalance> balances = new HashSet<>();

    /**
     * Пустой конструктор
     */
    public User() {
    }

    /**
     * Конструктор для создания пользователя.
     * @param userName - имя пользователя - предпоалагается строковоя переменная Имя + Фамилия.
     * @param userEmail - email пользователя.
     * @param password - пароль, в БД хранится в виде хешированном виде.
     */
    public User(String userName, String userEmail, String password) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<UserBalance> getBalances() {
        return balances;
    }

    public void setBalances(Set<UserBalance> balances) {
        this.balances = balances;
    }

}
