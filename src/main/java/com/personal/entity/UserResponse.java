package com.personal.entity;

import com.personal.model.UserInfo;
import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;

    public UserResponse(UserInfo user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.createdAt = user.getCreatedAt();
    }

    public Long getId() {return id;}
    public String getEmail() {return email;}
    public String getName() {return name;}
    public LocalDateTime getCreatedAt() {return createdAt;}
}
