package com.aidar.tuneflow.mapper;

import com.aidar.tuneflow.dto.request.UserRequest;
import com.aidar.tuneflow.dto.response.UserResponse;
import com.aidar.tuneflow.model.Role;
import com.aidar.tuneflow.model.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {
    User toEntity(UserRequest request);
    UserResponse toResponse(User user);

    default List<Role> mapRoles(List<String> roles) {
        return roles.stream()
                .map(roleName -> new Role(roleName))  // Créez l'objet Role avec le nom du rôle
                .collect(Collectors.toList());
    }
}