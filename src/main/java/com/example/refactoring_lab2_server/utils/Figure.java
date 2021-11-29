package com.example.refactoring_lab2_server.utils;

import com.example.refactoring_lab2_server.enums.FigureEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class Figure {
    private List<FiguresService.Coordinate> coordinates = new ArrayList<>();
    private FigureEnum figure;

    public static Figure getFigure(List<FiguresService.Vector> vectors) {
        Figure figure = new Figure();
        figure.figure = getFigure(vectors.size());
        figure.coordinates = vectors.stream()
            .map(vector -> vector.start)
            .collect(Collectors.toList());

        return figure;
    }

    private static FigureEnum getFigure(int amountOfEdges) {
        if (amountOfEdges == 3) return FigureEnum.TRIANGLE;
        if (amountOfEdges == 4) return FigureEnum.RECTANGLE;
        if (amountOfEdges == 5) return FigureEnum.PENTAGON;
        if (amountOfEdges == 6) return FigureEnum.HEXAGON;

        return FigureEnum.OTHER;
    }

    public String getCoordinatesString() {
        return "(" + coordinates.stream()
            .map(c -> "[" + c.x + "," + c.y + "]")
            .collect(Collectors.joining()) + ")";
    }
}
