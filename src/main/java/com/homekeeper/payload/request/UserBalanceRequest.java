package com.homekeeper.payload.request;

import javax.validation.constraints.NotBlank;

public class UserBalanceRequest {
    @NotBlank
    private String balanceSumOfBalance;

    public String getBalanceSumOfBalance() {
        return balanceSumOfBalance;
    }

    public void setBalanceSumOfBalance(String balanceSumOfBalance) {
        this.balanceSumOfBalance = balanceSumOfBalance;
    }
}
