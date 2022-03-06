package com.okarin.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.Point;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public abstract class Shape2D extends AbstractShape{
    private Color bodyColor;

    public Shape2D(Point location, Integer frameThickness, Color frameColor, Color bodyColor) {
        super(location, frameThickness, frameColor);
        this.bodyColor = bodyColor;
    }
}
