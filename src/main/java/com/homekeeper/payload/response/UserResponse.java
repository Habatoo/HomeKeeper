package com.homekeeper.payload.response;

import com.homekeeper.models.Role;
import com.homekeeper.models.UserBalance;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserResponse {
    private String userName;
    private String userEmail;
    private LocalDateTime creationDate;
    private Set<Role> roles;
    private Set<UserBalance> balances;

    public UserResponse(String userName, String userEmail, LocalDateTime creationDate, Set<Role> roles, Set<UserBalance> balances) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.creationDate = creationDate;
        this.roles = roles;
        this.balances = balances;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
