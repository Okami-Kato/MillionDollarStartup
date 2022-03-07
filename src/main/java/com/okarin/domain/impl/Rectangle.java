package com.okarin.domain.impl;

import com.okarin.domain.Shape2D;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import static java.lang.Math.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Rectangle extends Shape2D {
    private Point corner;

    private static Point getLeftUpperCorner(Point firstCorner, Point secondCorner) {
        return new Point(min(firstCorner.x, secondCorner.x), min(firstCorner.y, secondCorner.y));
    }

    private static Point getRightLowerCorner(Point firstCorner, Point secondCorner) {
        return new Point(max(firstCorner.x, secondCorner.x), max(firstCorner.y, secondCorner.y));
    }

    public Rectangle(Point location, Point corner, Integer frameThickness, Color frameColor, Color fillColor) {
        super(location, frameThickness, frameColor, fillColor);
        this.corner = corner;
    }

    @Override
    public void draw(Graphics2D g) {
        Point leftUpperCorner = getLeftUpperCorner(getLocation(), corner);
        Point rightLowerCorner = getRightLowerCorner(getLocation(), corner);
        g.setStroke(new BasicStroke(getFrameThickness()));
        g.setColor(getFillColor());
        int width = rightLowerCorner.x - leftUpperCorner.x;
        int height = rightLowerCorner.y - leftUpperCorner.y;
        g.fillRect(leftUpperCorner.x, leftUpperCorner.y, width, height);
        g.setColor(getFrameColor());
        g.drawRect(leftUpperCorner.x, leftUpperCorner.y, width, height);
    }

    @Override
    public void move(Point point) {
        corner.translate(point.x - getLocation().x, point.y - getLocation().y);
        super.move(point);
    }

    @Override
    public boolean contains(Point point) {
        return point.x >= getLocation().x && point.x <= corner.x &&
                point.y >= getLocation().y && point.y <= corner.y;
    }

    public void normalize(){
        Point tmp = getLocation();
        setLocation(getLeftUpperCorner(tmp, corner));
        this.corner = getRightLowerCorner(tmp, corner);
    }
}
