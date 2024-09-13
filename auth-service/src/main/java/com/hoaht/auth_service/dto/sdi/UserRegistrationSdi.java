package com.hoaht.auth_service.dto.sdi;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationSdi {
    private String username;
    private String email;
    private String password;
}
