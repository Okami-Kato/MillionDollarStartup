package com.okarin.action.adapters;

import com.okarin.action.ActionEnum;
import com.okarin.domain.impl.Line;
import com.okarin.domain.impl.Ray;
import com.okarin.domain.impl.Segment;
import com.okarin.domain.Shape;
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
public class SegmentAdapter extends MouseAdapter {
    @Autowired
    @Qualifier("frame")
    private Supplier<Color> frameColor;

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
        Shape newShape = switch (action) {
            case SEGMENT -> new Segment(e.getPoint(), e.getPoint(), frameThickness, frameColor);
            case RAY -> new Ray(e.getPoint(), e.getPoint(), frameThickness, frameColor);
            case LINE -> new Line(e.getPoint(), e.getPoint(), frameThickness, frameColor);
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
        if (action == ActionEnum.SEGMENT || action == ActionEnum.LINE || action == ActionEnum.RAY) {
            Optional<Shape> currentShape = currentShapeSupplier.get();
            currentShape.ifPresent(s -> ((Segment) s).setEndPoint(e.getPoint()));
            repaintEventPublisher.publish();
        }
    }
}
