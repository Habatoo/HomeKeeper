package com.homekeeper.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

/**
 * Модель ролей. Записывается в БД в таблицу с имененм roles.
 * @version 0.013
 * @author habatoo
 *
 * @param "id" - primary key таблицы roles.
 * @param "roleName" - наименовение роли.
 * @see ERoles (перечень возможных ролей пользователя).
 */
@Entity
@Table(name = "roles")
@ToString(of = {"id", "roleName"})
@EqualsAndHashCode(of = {"id"})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private ERoles roleName;

    public Role() {

    }

    public Role(ERoles roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ERoles getRoleName() {
        return roleName;
    }

    public void setRoleName(ERoles roleName) {
        this.roleName = roleName;
    }
}
