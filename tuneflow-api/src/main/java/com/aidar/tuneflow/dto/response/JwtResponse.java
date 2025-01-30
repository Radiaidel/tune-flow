package com.aidar.tuneflow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String login;
    private String[] roles;

    public JwtResponse(String accessToken, String id, String login, String[] roles) {
        this.token = accessToken;
        this.id = id;
        this.login = login;
        this.roles = roles;
    }
}

