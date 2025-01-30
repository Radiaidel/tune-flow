package com.aidar.tuneflow.dto.response;

import com.aidar.tuneflow.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private String id;
    private String login;
    private Boolean active;
    private List<Role> roles;
}