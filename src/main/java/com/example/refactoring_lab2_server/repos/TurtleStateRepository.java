package com.example.refactoring_lab2_server.repos;

import com.example.refactoring_lab2_server.entities.TurtleState;
import com.example.refactoring_lab2_server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TurtleStateRepository extends JpaRepository<TurtleState, Long> {
    TurtleState findTopByOwnerOrderByCreateDesc(User user);
    List<TurtleState> findAllByOwner(User user);
}