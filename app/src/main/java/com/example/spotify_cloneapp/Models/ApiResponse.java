package com.example.spotify_cloneapp.Models;

public class ApiResponse {
    private String message;
    private String error;
    private Account account;

    // Các phương thức getter và setter
    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }

    public Account getAccount() {
        return account;
    }
}