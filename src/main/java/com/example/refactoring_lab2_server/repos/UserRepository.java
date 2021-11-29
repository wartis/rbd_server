package com.example.refactoring_lab2_server.repos;

import com.example.refactoring_lab2_server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String login, String password);
    User findByUsername(String username);
}