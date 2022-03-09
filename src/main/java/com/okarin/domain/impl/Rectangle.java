package com.okarin.domain.impl;

import com.okarin.domain.RectangleContainedShape;
import com.okarin.domain.Shape2D;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class Rectangle extends Shape2D implements RectangleContainedShape {
    private Point corner;

    private int width;

    private int height;

    public Rectangle(Point location, Point corner, Integer frameThickness, Color frameColor, Color fillColor) {
        super(location, frameThickness, frameColor, fillColor);
        this.corner = corner;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setStroke(new BasicStroke(getFrameThickness()));
        g.setColor(getFillColor());
        g.fillRect(corner.x, corner.y, width, height);
        g.setColor(getFrameColor());
        g.drawRect(corner.x, corner.y, width, height);
    }

    @Override
    public void move(Point point) {
        corner.translate(point.x - getLocation().x, point.y - getLocation().y);
        super.move(point);
    }

    @Override
    public boolean contains(Point point) {
        return corner.x <= point.x && point.x <= corner.x + width &&
                corner.y <= point.y && point.y <= corner.y + height;
    }

    @Override
    public void setCorner(Point point) {
        this.corner = point;
        normalizeCorner();
        width = 2 * (getLocation().x - corner.x);
        height = 2 * (getLocation().y - corner.y);
    }

    protected void normalizeCorner() {
        int deltaX = getLocation().x - corner.x;
        if (deltaX < 0) {
            corner.translate(2 * deltaX, 0);
        }
        int deltaY = getLocation().y - corner.y;
        if (deltaY < 0) {
            corner.translate(0, 2 * deltaY);
        }
    }
}
