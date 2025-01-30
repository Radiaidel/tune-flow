package com.aidar.tuneflow.service;

import com.aidar.tuneflow.dto.request.LoginRequest;
import com.aidar.tuneflow.dto.response.JwtResponse;

public interface AuthService {
    JwtResponse login(LoginRequest loginRequest);
}

