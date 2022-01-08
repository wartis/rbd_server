package com.example.refactoring_lab2_server.services;

import com.example.refactoring_lab2_server.entities.User;
import com.example.refactoring_lab2_server.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;

    public User getFromContext() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return getByUsername(authentication.getName());
    }


    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
