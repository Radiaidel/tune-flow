package com.aidar.tuneflow.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class UserRequest {
    private String login;
    private String password;
    private List<String> roles;
}