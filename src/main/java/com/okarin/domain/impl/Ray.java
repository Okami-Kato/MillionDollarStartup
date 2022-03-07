package com.okarin.domain.impl;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.awt.Color;
import java.awt.Point;

import static java.awt.Toolkit.getDefaultToolkit;

@ToString
@EqualsAndHashCode(callSuper = true)
public class Ray extends Segment {
    public Ray(Point location, Point endPoint, Integer frameThickness, Color frameColor) {
        super(location, extendToWindowBorder(location, endPoint), frameThickness, frameColor);
    }

    protected static Point extendToWindowBorder(Point start, Point end) {
        Point result = new Point();
        double deltaX = end.x - start.x;
        double deltaY = end.y - start.y;
        if (deltaX == 0 && deltaY == 0) {
            return start;
        }
        if (Math.abs(deltaX) < Math.abs(deltaY)) {
            double height = (deltaY < 0) ? -1 : (getDefaultToolkit().getScreenSize().getHeight() + 1);
            result.setLocation(deltaX / deltaY * (height - start.y) + start.x, height);
        } else {
            double width = (deltaX < 0) ? -1 : (getDefaultToolkit().getScreenSize().getWidth() + 1);
            result.setLocation(width, deltaY / deltaX * (width - start.x) + start.y);
        }
        return result;
    }

    @Override
    public void setEndPoint(Point endPoint){
        super.setEndPoint(extendToWindowBorder(getLocation(), endPoint));
    }
}
