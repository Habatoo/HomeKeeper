package com.homekeeper.payload.response;

import com.homekeeper.models.User;

import java.time.LocalDateTime;

public class UserBalanceResponse {
    private Long id;
    private LocalDateTime balanceDate;
    private String balanceSumOfBalance;
    private User user;

    public UserBalanceResponse(Long id, LocalDateTime balanceDate, String balanceSumOfBalance, User user) {
        this.id = id;
        this.balanceDate = balanceDate;
        this.balanceSumOfBalance = balanceSumOfBalance;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(LocalDateTime balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getBalanceSumOfBalance() {
        return balanceSumOfBalance;
    }

    public void setBalanceSumOfBalance(String balanceSumOfBalance) {
        this.balanceSumOfBalance = balanceSumOfBalance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
