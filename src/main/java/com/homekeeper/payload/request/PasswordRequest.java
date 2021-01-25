package com.homekeeper.payload.request;

import javax.validation.constraints.NotBlank;

public class PasswordRequest {
    @NotBlank
    private String userName;

    @NotBlank
    private String secretKey;

    @NotBlank
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
