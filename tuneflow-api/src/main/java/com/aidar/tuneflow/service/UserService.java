package com.aidar.tuneflow.service;

import com.aidar.tuneflow.dto.request.UserRequest;
import com.aidar.tuneflow.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponse register(UserRequest request);
    UserResponse getUserById(String id);
    Page<UserResponse> getAllUsers(Pageable pageable);
    UserResponse updateUserRoles(String id, List<String> roleNames);
}
