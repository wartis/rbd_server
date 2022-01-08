package com.example.refactoring_lab2_server.utils;

import com.example.refactoring_lab2_server.enums.ColorEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Vector {
    private Coordinate start;
    private Coordinate end;
    private boolean visible;
    private ColorEnum color;

    Vector(double startX, double startY, double endX, double endY, boolean visible, ColorEnum color) {
        start = new Coordinate(startX, startY);
        end = new Coordinate(endX, endY);
        this.visible = visible;
        this.color = color;
    }

    public String getCommand() {
        if (start != end) {
            return "move to " + "[" + end.getX() + "," + end.getY() + "] with " + (visible ? "pan up" : "pan down");
        }

        return "";
    }
}
