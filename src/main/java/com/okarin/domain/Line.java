package com.okarin.domain;

import lombok.Getter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

@Getter
public class Line extends Ray {
    private Point startPoint;

    public Line(Point location, Point endPoint, Integer frameThickness, Color frameColor) {
        super(location,
                extendToWindowBorder(location, endPoint),
                frameThickness,
                frameColor);
        startPoint = extendToWindowBorder(endPoint, location);
    }

    @Override
    public void setEndPoint(Point endPoint) {
        super.setEndPoint(endPoint);
        startPoint = extendToWindowBorder(endPoint, getLocation());
    }

    public void setStartPoint(Point startPoint){
        startPoint = extendToWindowBorder(getLocation(), startPoint);
        setEndPoint(extendToWindowBorder(startPoint, getLocation()));
    }

    @Override
    public void draw(Graphics2D g){
        g.setStroke(new BasicStroke(getFrameThickness()));
        g.setColor(getFrameColor());
        g.drawLine(startPoint.x, startPoint.y, getEndPoint().x, getEndPoint().y);
    }
}
