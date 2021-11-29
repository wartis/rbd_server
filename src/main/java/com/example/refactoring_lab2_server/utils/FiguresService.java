package com.example.refactoring_lab2_server.utils;

import com.example.refactoring_lab2_server.entities.TurtleState;
import com.example.refactoring_lab2_server.enums.ColorEnum;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FiguresService {

    @Getter
    @EqualsAndHashCode
    public class Vector {
        Coordinate start;
        Coordinate end;
        boolean visible;
        ColorEnum color;

        Vector(double startX, double startY, double endX, double endY, boolean visible, ColorEnum color) {
            start = new Coordinate(startX, startY);
            end = new Coordinate(endX, endY);
            this.visible = visible;
            this.color = color;
        }

        public String getCommand() {
            if (start != end) {
                return "move to " + "[" + end.x + "," + end.y + "] with " + (visible ? "pan up" : "pan down");
            }

            return "";
        }
    }

    @AllArgsConstructor
    @EqualsAndHashCode
    public class Coordinate {
        double x;
        double y;
    }

    public List<Figure> getFigures(List<TurtleState> states) {
        final List<Vector> vectors = getRightVectors(states);
        List<Vector> vectorsToCheck = new ArrayList<>();

        final List<Figure> figures = new ArrayList<>();

        for (int amountOfEdges = 3; amountOfEdges <= 6; amountOfEdges++) {
            for (Vector vector : vectors) {
                //ужасный код
                if (vectorsToCheck.size() == amountOfEdges) {
                    vectorsToCheck.add(vector);
                    vectorsToCheck.remove(0);
                }
                if (vectorsToCheck.size() < amountOfEdges) {
                    vectorsToCheck.add(vector);
                }
                if (vectorsToCheck.size() == amountOfEdges) {
                    if (checkIfClosedOutline(vectorsToCheck)) {
                        final Figure figure = Figure.getFigure(vectorsToCheck);
                        figures.add(figure);
                    }
                }
            }
            vectorsToCheck = new ArrayList<>();
        }

        return figures;
    }

    private List<Vector> getRightVectors(List<TurtleState> states) {
        List<Vector> list = getAllVectors(states);

        list = list.stream()
            .filter(vector -> !vector.start.equals(vector.end))
            .filter(vector -> vector.visible)
            .collect(Collectors.toList());

        return list;
    }

    public List<Vector> getAllVectors(List<TurtleState> states) {
        List<Vector> list = new ArrayList<>();

        for (int i=1; i < states.size(); i++) {
            final TurtleState first = states.get(i - 1);
            final TurtleState second = states.get(i);
            list.add(new Vector(
                first.getX(),
                first.getY(),
                second.getX(),
                second.getY(),
                second.isPenState(),
                second.getColor()
            ));
        }

        return list;
    }


    public boolean checkIfClosedOutline(List<Vector> list) {
        final Coordinate start = list.get(0).getStart();

        for (int vectorIdx = 0; vectorIdx < list.size() - 1; vectorIdx++) {
            final Vector cur = list.get(vectorIdx);
            final Vector next = list.get(vectorIdx + 1);

            if (cur.end.equals(start)) return false;
            if (cur.end.equals(next.start)) continue;

            return false;
        }

        final Coordinate end   = list.get(list.size() - 1).getEnd();

        return start.equals(end);
    }
}
