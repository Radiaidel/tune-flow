package com.aidar.tuneflow.security;

import com.aidar.tuneflow.model.User;
import com.aidar.tuneflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByLogin(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
            return new UserPrincipal(
                    user.getId(),
                    user.getLogin(),
                    user.getPassword(),
                    user.getActive(),
                    user.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(
                                    role.getName().startsWith("ROLE_") ? role.getName() : "ROLE_" + role.getName()
                            ))
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error loading user by username: " + username, e);
        }
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByLogin(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
//
//        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(
//                        role.getName().startsWith("ROLE_") ? role.getName() : "ROLE_" + role.getName()
//                ))
//                .collect(Collectors.toList());
//        return new UserPrincipal(
//                user.getId(),
//                user.getLogin(),
//                user.getPassword(),
//                user.getActive(),
//                authorities
//        );
//    }
}