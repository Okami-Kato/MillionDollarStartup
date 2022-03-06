package com.okarin.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Segment extends AbstractShape{
    private Point endPoint;

    @Builder
    public Segment(Point location, Point endPoint, Integer frameThickness, Color frameColor) {
        super(location, frameThickness, frameColor);
        this.endPoint = endPoint;
    }

    @Override
    public void draw(Graphics2D g) {
        Point startPoint = getLocation();
        g.setStroke(new BasicStroke(getFrameThickness()));
        g.setColor(getFrameColor());
        g.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
    }

    @Override
    public void move(Point point) {
        Point location = getLocation();
        setEndPoint(new Point(endPoint.x + point.x - location.x, endPoint.y + point.y - location.y));
        super.move(point);
    }

    @Override
    public boolean contains(Point point) {
        Point location = getLocation();
        int a = endPoint.y - location.y;
        int b = endPoint.x - location.x;
        double d = (a * point.x - b * point.y + b * location.y - a * location.x) / (Math.sqrt(a * a + b * b));
        return Math.abs(d) < (double) getFrameThickness() / 2;
    }
}
