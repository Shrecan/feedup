package com.feedup.feedup.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.feedup.feedup.dto.LoginRequest;
import com.feedup.feedup.dto.LoginResponse;
import com.feedup.feedup.service.AdminAuthService;

@RestController
@CrossOrigin("*")
public class AuthController {

    private final AdminAuthService adminAuthService;

    public AuthController(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        if (adminAuthService.validLogin(request.username(), request.password())) {
            return ResponseEntity.ok(new LoginResponse(true));
        }
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}
