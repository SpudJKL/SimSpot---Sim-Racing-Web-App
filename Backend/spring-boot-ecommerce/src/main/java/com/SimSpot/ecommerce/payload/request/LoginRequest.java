package com.SimSpot.ecommerce.payload.request;

/**
 *
 */
public class LoginRequest {
    // Variables
    private String username;

    private String password;

    // Methods
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}