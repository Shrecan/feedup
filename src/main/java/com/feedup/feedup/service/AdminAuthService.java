package com.feedup.feedup.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AdminAuthService {

    private final String adminUsername;
    private final String adminPassword;

    public AdminAuthService(
            @Value("${admin.username}") String adminUsername,
            @Value("${admin.password}") String adminPassword) {
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    public boolean validLogin(String username, String password) {
        return adminUsername.equals(username) && adminPassword.equals(password);
    }

    public boolean validPassword(String password) {
        return adminPassword.equals(password);
    }
}
