package com.okarin.domain.impl;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

public class DynamicPolygon extends Polygon{

    public DynamicPolygon(Point location, int frameWidth, Color frameColor, Color fillColor) {
        super(location, frameWidth, frameColor, fillColor);
    }

    public DynamicPolygon(Point location, List<Point> points, int frameWidth, Color frameColor, Color fillColor) {
        super(location, points, frameWidth, frameColor, fillColor);
    }

    public void addPoint(Point point) {
        points.add(point);
        setLocation(computeCenter());
    }

    public void setLastPoint(Point point) {
        points.remove(points.size() - 1);
        points.add(point);
        setLocation(computeCenter());
    }
}
