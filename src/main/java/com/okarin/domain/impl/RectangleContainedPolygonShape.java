package com.okarin.domain.impl;

import com.okarin.domain.RectangleContainedShape;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

public abstract class RectangleContainedPolygonShape extends Polygon implements RectangleContainedShape {
    public RectangleContainedPolygonShape(Point location, Point corner, int frameWidth, Color frameColor, Color fillColor) {
        super(location, frameWidth, frameColor, fillColor);
        setPoints(getVertices(location, corner));
    }

    protected abstract List<Point> getVertices(Point center, Point corner);

    @Override
    public void setCorner(Point corner){
        setPoints(getVertices(getLocation(), corner));
    }
}
