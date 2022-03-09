package com.okarin.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.Color;
import java.awt.Point;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public abstract class Shape2D extends AbstractShape {
    private Color fillColor;

    public Shape2D(Point location, Integer frameThickness, Color frameColor, Color fillColor) {
        super(location, frameThickness, frameColor);
        this.fillColor = fillColor;
    }
}


