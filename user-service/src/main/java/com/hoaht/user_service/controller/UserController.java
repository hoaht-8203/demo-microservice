package com.hoaht.user_service.controller;

import com.hoaht.user_service.entities.User;
import com.hoaht.user_service.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PostMapping("/authenticate")
    public User authenticate(@RequestBody User user) {
        return userService.authentication(user.getEmail(), user.getPassword());
    }

    @GetMapping(value = "/secure")
    public String getSecure() {
        return "Secure endpoint available";
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable String id) {
        return userService.getById(id);
    }
}
