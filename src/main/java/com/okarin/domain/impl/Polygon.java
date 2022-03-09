package com.okarin.domain.impl;

import com.okarin.domain.Shape2D;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Polygon extends Shape2D {

    private final List<Point> points = new ArrayList<>();

    public Polygon(Point location, List<Point> points, int frameWidth, Color frameColor, Color fillColor) {
        super(location, frameWidth, frameColor, fillColor);
        this.points.addAll(points);
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

    @Override
    public void draw(Graphics2D g) {
        g.setStroke(new BasicStroke(getFrameThickness()));
        g.setColor(getFillColor());

        int[] xPoints = new int[points.size()];
        int[] yPoints = new int[points.size()];
        for (int i = 0; i < points.size(); i++) {
            xPoints[i] = points.get(i).x;
            yPoints[i] = points.get(i).y;
        }

        g.fillPolygon(xPoints, yPoints, points.size());
        g.setColor(getFrameColor());
        g.drawPolygon(xPoints, yPoints, points.size());
    }

    @Override
    public boolean contains(Point point) {
        int i, j;
        boolean result = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if ((points.get(i).y > point.y) != (points.get(j).y > point.y) &&
                    (point.x < (points.get(j).x - points.get(i).x) * (point.y - points.get(i).y) / (points.get(j).y - points.get(i).y) + points.get(i).x)) {
                result = !result;
            }
        }
        return result;
    }

    @Override
    public void move(Point pt) {
        int dx = pt.x - getLocation().x;
        int dy = pt.y - getLocation().y;
        points.forEach(p -> p.translate(dx, dy));
        super.move(pt);
    }

    private Point computeCenter() {
        double x = 0.;
        double y = 0.;
        int pointCount = points.size();

        for (int i = 0; i < pointCount - 1; i++) {
            Point point = points.get(i);
            x += point.getX();
            y += point.getY();
        }

        x = x / pointCount;
        y = y / pointCount;

        return new Point((int) x, (int) y);
    }
}