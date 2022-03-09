package com.okarin.action.adapters;

import com.okarin.action.ActionEnum;
import com.okarin.domain.Shape;
import com.okarin.domain.impl.Polygon;
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
public class PolygonAdapter extends MouseAdapter {
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
        Optional<Shape> currentShape = currentShapeSupplier.get();
        if (currentAction.get() == ActionEnum.POLYGON) {
            if (currentShape.isPresent() && currentShape.get().getClass().equals(Polygon.class)) {
                ((Polygon) currentShape.get()).addPoint(e.getPoint());
            } else {
                Polygon polygon = new Polygon(e.getPoint(), List.of(e.getPoint()), frameThickness.get(), frameColor.get(), fillColor.get());
                shapes.add(polygon);
                currentShapeConsumer.accept(polygon);
            }
            repaintEventPublisher.publish();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Optional<Shape> currentShape = currentShapeSupplier.get();
        if (currentShape.isPresent() && currentAction.get() == ActionEnum.POLYGON) {
            ((Polygon) currentShape.get()).setLastPoint(e.getPoint());
            repaintEventPublisher.publish();
        }
    }
}
