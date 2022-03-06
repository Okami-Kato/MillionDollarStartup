package com.okarin.domain;

import java.awt.Graphics2D;
import java.awt.Point;

public interface Shape {
    void draw(Graphics2D graphics2D);

    void move(Point point);

    boolean contains(Point point);

    Point getLocation();
}
