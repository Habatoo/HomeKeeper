package com.homekeeper.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

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
