package com.okarin.domain.impl;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Ellipse extends Rectangle {
    public Ellipse(Point location, Point corner, Integer frameThickness, Color frameColor, Color fillColor) {
        super(location, corner, frameThickness, frameColor, fillColor);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setStroke(new BasicStroke(getFrameThickness()));
        g.setColor(getFillColor());
        g.fillOval(getCorner().x, getCorner().y, getWidth(), getHeight());
        g.setColor(getFrameColor());
        g.drawOval(getCorner().x, getCorner().y, getWidth(), getHeight());
    }

    @Override
    public boolean contains(Point pt) {
        double a = (pt.getX() - getLocation().getX()) / getWidth();
        double b = (pt.getY() - getLocation().getY()) / getHeight();
        return 4 * (a * a + b * b) < 1;
    }
}
