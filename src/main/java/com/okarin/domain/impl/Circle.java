package com.okarin.domain.impl;

import java.awt.Color;
import java.awt.Point;

public class Circle extends Ellipse {
    public Circle(Point location, Point corner, Integer frameThickness, Color frameColor, Color fillColor) {
        super(location, corner, frameThickness, frameColor, fillColor);
    }

    @Override
    protected void normalizeCorner() {
        super.normalizeCorner();
        int radius = getCorner().x - getLocation().x;
        getCorner().setLocation(getCorner().x, getLocation().y + radius);
    }
}
