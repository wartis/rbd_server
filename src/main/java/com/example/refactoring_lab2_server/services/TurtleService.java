package com.example.refactoring_lab2_server.services;

import com.example.refactoring_lab2_server.entities.TurtleState;
import com.example.refactoring_lab2_server.entities.User;
import com.example.refactoring_lab2_server.enums.ColorEnum;
import com.example.refactoring_lab2_server.repos.TurtleStateRepository;
import com.example.refactoring_lab2_server.utils.Figure;
import com.example.refactoring_lab2_server.utils.FiguresService;
import com.example.refactoring_lab2_server.utils.Vector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor

@Service
public class TurtleService {

    private final FiguresService figuresService;

    private final TurtleStateRepository turtleStateRepository;

    private final UserService userService;

    public void initStateForUser(User user) {
        turtleStateRepository.save(new TurtleState(false, 0, 0, 0, ColorEnum.BLACK, user));
    }

    public String changeAngle(final int angle) {
        final User user = userService.getFromContext();
        final TurtleState lastState = turtleStateRepository.findTopByOwnerOrderByCreateDesc(user);
        final TurtleState newState = lastState.changeAngle(angle);
        turtleStateRepository.save(newState);

        return newState.toString();
    }

    public String move(final int steps) {
        final User user = userService.getFromContext();
        final TurtleState lastState = turtleStateRepository.findTopByOwnerOrderByCreateDesc(user);
        final TurtleState newState = lastState.move(steps);
        turtleStateRepository.save(newState);

        return newState.toString();
    }

    public String changeColor(final ColorEnum color) {
        final User user = userService.getFromContext();
        final TurtleState lastState = turtleStateRepository.findTopByOwnerOrderByCreateDesc(user);
        final TurtleState newState = lastState.changeColor(color);
        turtleStateRepository.save(newState);

        return newState.toString();
    }

    public String changePenState(final boolean pu) {
        final User user = userService.getFromContext();
        final TurtleState lastState = turtleStateRepository.findTopByOwnerOrderByCreateDesc(user);

        if (lastState.isPenState() == pu) {
            return "Pen is already " + (pu ? "put up" : "put down");
        }

        final TurtleState newState = lastState.changePen();
        turtleStateRepository.save(newState);
        return newState.toString();
    }

    public String getFigures() {
        final User user = userService.getFromContext();
        final List<TurtleState> all = turtleStateRepository.findAllByOwner(user);
        final List<Figure> figures = figuresService.getFigures(all);

        String response = "";
        int count = 1;
        for (Figure figure : figures) {
            final String figureName = figure.getFigure().name().toLowerCase();
            final String coordinatesString = figure.getCoordinatesString();
            response += count++ + ") " + figureName + " with coordinates " + coordinatesString + "\n";
        }

        return response;
    }

    public String getSteps() {
        final User user = userService.getFromContext();
        final List<TurtleState> all = turtleStateRepository.findAllByOwner(user);
        return figuresService.getAllVectors(all).stream()
            .map(Vector::getCommand)
            .filter(str -> !str.isEmpty())
            .map(str -> str + "\n")
            .collect(Collectors.joining());
    }
}
