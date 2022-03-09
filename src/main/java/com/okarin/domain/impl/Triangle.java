package com.okarin.domain.impl;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

public class Triangle extends RectangleContainedPolygonShape {
    public Triangle(Point location, Point corner, int frameWidth, Color frameColor, Color fillColor) {
        super(location, corner, frameWidth, frameColor, fillColor);
    }

    @Override
    protected List<Point> getVertices(Point center, Point corner) {
        return List.of(
                new Point(corner),
                new Point(2 * center.x - corner.x, corner.y),
                new Point(center.x, 2 * center.y - corner.y)
        );
    }
}
