package com.hoaht.user_service.services;

import com.hoaht.user_service.entities.User;
import com.hoaht.user_service.repos.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {
    private  final UserRepository userRepository;
    private RestTemplate restTemplate;

    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate  = restTemplate;
    }

    public User save(User user) {
        user.setRole("USER");
        user.setDepartmentId("DEP1");

        return userRepository.save(user);
    }

    public User getById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User authentication(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (BCrypt.checkpw(password, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
