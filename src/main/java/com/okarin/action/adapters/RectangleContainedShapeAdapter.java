package com.okarin.action.adapters;

import com.okarin.action.ActionEnum;
import com.okarin.domain.RectangleContainedShape;
import com.okarin.domain.Shape;
import com.okarin.domain.impl.Circle;
import com.okarin.domain.impl.Ellipse;
import com.okarin.domain.impl.Rectangle;
import com.okarin.domain.impl.Rhombus;
import com.okarin.domain.impl.Triangle;
import com.okarin.event.RepaintEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class RectangleContainedShapeAdapter extends MouseAdapter {
    @Autowired
    @Qualifier("frame")
    private Supplier<Color> frameColor;

    @Autowired
    @Qualifier("fill")
    private Supplier<Color> fillColor;

    @Autowired
    private Supplier<Integer> frameThickness;

    @Autowired
    private List<Shape> shapes;

    @Autowired
    private Supplier<ActionEnum> currentAction;

    @Autowired
    private Supplier<Optional<Shape>> currentShapeSupplier;

    @Autowired
    private Consumer<Shape> currentShapeConsumer;

    @Autowired
    private RepaintEventPublisher repaintEventPublisher;

    @Override
    public void mousePressed(MouseEvent e) {
        ActionEnum action = currentAction.get();
        final Integer frameThickness = this.frameThickness.get();
        final Color frameColor = this.frameColor.get();
        final Color fillColor = this.fillColor.get();
        Shape newShape = switch (action) {
            case RECTANGLE -> new Rectangle(e.getPoint(), e.getPoint(), frameThickness, frameColor, fillColor);
            case RHOMBUS -> new Rhombus(e.getPoint(), e.getPoint(), frameThickness, frameColor, fillColor);
            case TRIANGLE -> new Triangle(e.getPoint(), e.getPoint(), frameThickness, frameColor, fillColor);
            case ELLIPSE -> new Ellipse(e.getPoint(), e.getPoint(), frameThickness, frameColor, fillColor);
            case CIRCLE -> new Circle(e.getPoint(), e.getPoint(), frameThickness, frameColor, fillColor);
            default -> null;
        };
        if (newShape != null) {
            shapes.add(newShape);
            currentShapeConsumer.accept(newShape);
            repaintEventPublisher.publish();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        ActionEnum action = currentAction.get();
        if (action == ActionEnum.RECTANGLE || action == ActionEnum.ELLIPSE ||
                action == ActionEnum.CIRCLE || action == ActionEnum.RHOMBUS ||
                action == ActionEnum.TRIANGLE) {
            Optional<Shape> currentShape = currentShapeSupplier.get();
            currentShape.ifPresent(s -> ((RectangleContainedShape) s).setCorner(e.getPoint()));
            repaintEventPublisher.publish();
        }
    }
}
