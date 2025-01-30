package com.aidar.tuneflow.controller;


import com.aidar.tuneflow.dto.request.UserRequest;
import com.aidar.tuneflow.dto.response.UserResponse;
import com.aidar.tuneflow.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(request));
    }

    @GetMapping("/admin/users")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<Page<UserResponse>> getAllUsers(
//            @PageableDefault(size = 20, sort = "login") Pageable pageable) {
//        return ResponseEntity.ok(userService.getAllUsers(pageable));
//    }
//    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PutMapping("/admin/users/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> updateUserRoles(
            @PathVariable String id,
            @RequestBody List<String> roleNames) {
        return ResponseEntity.ok(userService.updateUserRoles(id, roleNames));
    }
}

