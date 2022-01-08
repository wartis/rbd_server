package com.example.refactoring_lab2_server.utils;

import com.example.refactoring_lab2_server.entities.TurtleState;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FiguresService {

    public List<Figure> getFigures(List<TurtleState> states) {
        final List<Vector> vectors = getRightVectors(states);
        List<Vector> vectorsToCheck = new ArrayList<>();

        final List<Figure> figures = new ArrayList<>();

        for (int amountOfEdges = 3; amountOfEdges <= 6; amountOfEdges++) {
            for (Vector vector : vectors) {
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
            .filter(vector -> !vector.getStart().equals(vector.getEnd()))
            .filter(Vector::isVisible)
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

            if (cur.getEnd().equals(start)) return false;
            if (cur.getEnd().equals(next.getStart())) continue;

            return false;
        }

        final Coordinate end   = list.get(list.size() - 1).getEnd();

        return start.equals(end);
    }
}
