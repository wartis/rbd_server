package com.example.refactoring_lab2_server.services;

import com.example.refactoring_lab2_server.entities.User;
import com.example.refactoring_lab2_server.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class UserService {

    private final UserRepository userRepository;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
