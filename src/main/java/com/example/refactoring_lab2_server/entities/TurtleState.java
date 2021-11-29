package com.example.refactoring_lab2_server.entities;

import com.example.refactoring_lab2_server.enums.ColorEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor

@Entity
public class TurtleState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "pen_state", nullable = false)
    private boolean penState;

    @Column(name = "angle")
    private int angle;

    @Column(name = "x", nullable = false)
    private double x;

    @Column(name = "y", nullable = false)
    private double y;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private ColorEnum color;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime create;

    @ManyToOne
    @JoinColumn(name = "owner_user_id")
    private User owner;

    public TurtleState(boolean penState, int angle, double x, double y, ColorEnum color, User owner) {
        this.penState = penState;
        this.angle = angle;
        this.x = x;
        this.y = y;
        this.color = color;
        this.owner = owner;
    }

    public TurtleState move(int steps) {
        double yOffset = Math.round(Math.sin(Math.toRadians(angle)) * steps * 100) / 100.0;
        double xOffset = (90 <= angle && angle <= 270 ? -1 : 1) * (Math.round(Math.sqrt(steps * steps - yOffset * yOffset) * 100) / 100.0);

        return new TurtleState(penState, angle, x + xOffset, y + yOffset, color, owner);
    }

    public TurtleState changeAngle(int angleTurn) {
        int curAngle = (this.angle + angleTurn) % 360;

        return new TurtleState(penState, curAngle, x, y, color, owner);
    }

    public TurtleState changePen() {
        return new TurtleState(!penState, angle, x, y, color, owner);
    }

    public TurtleState changeColor(ColorEnum color) {
        return new TurtleState(penState, angle, x, y, color, owner);
    }

    @Override
    public String toString() {
        return "Current color: " + color +
            ", pen state: " + (penState ? "put up" : "put down") +
            ", location(" + x + "," + y +")" +
            ", direction: " + angle + " degrees";
    }
}
