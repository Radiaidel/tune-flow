package com.aidar.tuneflow.service.impl;

import com.aidar.tuneflow.dto.request.UserRequest;
import com.aidar.tuneflow.dto.response.UserResponse;
import com.aidar.tuneflow.exception.ResourceNotFoundException;
import com.aidar.tuneflow.exception.UserAlreadyExistsException;
import com.aidar.tuneflow.mapper.UserMapper;
import com.aidar.tuneflow.model.Role;
import com.aidar.tuneflow.model.User;
import com.aidar.tuneflow.repository.RoleRepository;
import com.aidar.tuneflow.repository.UserRepository;
import com.aidar.tuneflow.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponse register(UserRequest request) {
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with this login");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);

        // Vérifier et attribuer les rôles fournis par l'utilisateur
        List<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName)))
                .collect(Collectors.toList());

        user.setRoles(roles);

        return userMapper.toResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(String id) {
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toResponse);
    }

    @Override
    @Transactional
    public UserResponse updateUserRoles(String id, List<String> roleNames) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Role> roles = roleNames.stream()
                .map(name -> roleRepository.findByName(name)
                        .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + name)))
                .collect(Collectors.toList());
        user.setRoles(roles);
        return userMapper.toResponse(userRepository.save(user));
    }
}
