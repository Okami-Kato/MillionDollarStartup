package com.okarin.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.awt.Color;
import java.awt.Point;

@Data
@AllArgsConstructor
public abstract class AbstractShape implements Shape {
    private Point location;

    private Integer frameThickness;

    private Color frameColor;

    @Override
    public void move(Point point) {
        setLocation(point);
    }
}