package com.example.refactoring_lab2_server.controller;

import com.example.refactoring_lab2_server.enums.ColorEnum;
import com.example.refactoring_lab2_server.services.TurtleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor

@RequestMapping("/turtle/api")
@RestController
public class CommandsController {
    private final TurtleService turtleService;

    @GetMapping("/move")
    public String move(@RequestParam int steps) {
        return turtleService.move(steps);
    }

    @GetMapping("/angle")
    public String angle(@RequestParam int angle) {
        return turtleService.changeAngle(angle);
    }

    @GetMapping("/pd")
    public String pd() {
        return turtleService.changePenState(false);
    }

    @GetMapping("/pu")
    public String pu() {
        return turtleService.changePenState(true);
    }

    @GetMapping("/color")
    public String color(@RequestParam ColorEnum color) {
        return turtleService.changeColor(color);
    }

    @GetMapping("/steps")
    public String listSteps() {
        return turtleService.getSteps();
    }

    @GetMapping("/figures")
    public String listFigures() {
        return turtleService.getFigures();
    }
}
