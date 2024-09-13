package com.hoaht.user_service.entities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "users")
public class User {
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    private String firstName;
    private String lastName;
    private String email;
    private String departmentId;
    private String password;
    private String role;
}
