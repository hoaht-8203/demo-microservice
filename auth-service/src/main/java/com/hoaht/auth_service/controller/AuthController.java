package com.hoaht.auth_service.controller;

import com.hoaht.auth_service.entities.AuthRequest;
import com.hoaht.auth_service.entities.AuthResponse;
import com.hoaht.auth_service.entities.SignInRequest;
import com.hoaht.auth_service.entities.SignInResponse;
import com.hoaht.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.register(authRequest));
    }

    @PostMapping(value = "/login")
    public ResponseEntity<SignInResponse> login(@RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authService.login(signInRequest));
    }

    @GetMapping(value = "/test")
    public String test() {
        return "Hello World";
    }

    @GetMapping(value = "/auth/need")
    public String needAuth() {
        return "Admin page need";
    }
}
