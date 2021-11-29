package com.example.refactoring_lab2_server;

import com.example.refactoring_lab2_server.entities.User;
import com.example.refactoring_lab2_server.enums.ColorEnum;
import com.example.refactoring_lab2_server.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor

@RequestMapping("/turtle/api")
@RestController
@SpringBootApplication
public class RefactoringLab2ServerApplication {

    private final TurtleService turtleService;

    private final UserService userService;

    @GetMapping("/move")
    public String move(@RequestParam int steps, Principal principal) {
        final User user = userService.getByUsername(principal.getName());
        final String response = turtleService.move(steps, user);

        return response;
    }

    @GetMapping("/angle")
    public String angle(@RequestParam int angle, Principal principal) {
        final User user = userService.getByUsername(principal.getName());
        return turtleService.changeAngle(angle, user);
    }

    @GetMapping("/pd")
    public String pd(Principal principal) {
        final User user = userService.getByUsername(principal.getName());
        return turtleService.changePenState(false, user);
    }

    @GetMapping("/pu")
    public String pu(Principal principal) {
        final User user = userService.getByUsername(principal.getName());
        return turtleService.changePenState(true, user);
    }

    @GetMapping("/color")
    public String color(@RequestParam ColorEnum color, Principal principal) {
        final User user = userService.getByUsername(principal.getName());
        return turtleService.changeColor(color, user);
    }

    @GetMapping("/steps")
    public String listSteps(Principal principal) {
        final User user = userService.getByUsername(principal.getName());
        return turtleService.getSteps(user);
    }

    @GetMapping("/figures")
    public String listFigures(Principal principal) {
        final User user = userService.getByUsername(principal.getName());
        return turtleService.getFigures(user);
    }


    public static void main(String[] args) {
        SpringApplication.run(RefactoringLab2ServerApplication.class, args);
    }

}
