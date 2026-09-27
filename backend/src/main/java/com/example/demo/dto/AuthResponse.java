package com.example.demo.dto;

public class AuthResponse {

    private String token;
    private String role;

    public AuthResponse(String token, String role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return this.token;
    }

    public String getRole(){ return this.role; }
}