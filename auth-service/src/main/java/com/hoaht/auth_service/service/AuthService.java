package com.hoaht.auth_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hoaht.auth_service.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class AuthService {
    private final JwtUtil jwtUtil;
    private final RestTemplate restTemplate;

    @Autowired
    public AuthService(RestTemplate restTemplate, JwtUtil jwtUtil) {
        this.restTemplate = restTemplate;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(AuthRequest authRequest) {
        //do validation if user already exists
        authRequest.setPassword(BCrypt.hashpw(authRequest.getPassword(), BCrypt.gensalt()));

        UserVO userVO = restTemplate.postForObject("http://user-service/users", authRequest, UserVO.class);
        System.out.println("UserVO register: " + userVO.toString());

        Assert.notNull(userVO, "Failed to register user. Please try again later");

        return new AuthResponse(userVO, "Sign up successfully");
    }

    public SignInResponse login(SignInRequest signInRequest) {

        UserVO userVO = restTemplate.postForObject("http://user-service/users/authenticate", signInRequest, UserVO.class);
        System.out.println("UserVO log in: " + userVO.toString());

        Assert.notNull(userVO, "Failed to login user. Please try again later");

        String accessToken = null;
        String refreshToken = null;
        try {
            accessToken = jwtUtil.generateToken(userVO, 10000000000L);
            refreshToken = jwtUtil.generateToken(userVO, 100000000000L);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (accessToken == null || refreshToken == null) {
            throw new RuntimeException("Failed to login user. Please try again later");
        }

        return new SignInResponse(accessToken, refreshToken, "Login successfully");
    }

    public UserVO getUserById(String userId, String token) {
        UserVO user = restTemplate.getForObject("http://user-service/users/{id}", UserVO.class, Map.of("id", userId));
        return user;
    }
}
