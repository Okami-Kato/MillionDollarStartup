package com.okarin.action.adapters;

import com.okarin.action.ActionEnum;
import com.okarin.domain.Shape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Service
public class MoveAction extends MouseAdapter {
    @Autowired
    private List<Shape> shapes;

    @Autowired
    private Supplier<ActionEnum> currentAction;

    @Autowired
    private JFrame frame;

    @Autowired
    private Supplier<Optional<Shape>> currentShapeSupplier;

    @Autowired
    private Consumer<Shape> currentShapeConsumer;

    private boolean isDragged = false;

    @Override
    public void mouseEntered(MouseEvent e) {
        if (currentAction.get() == ActionEnum.MOVE) {
            frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
        } else {
            frame.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && currentAction.get() == ActionEnum.MOVE) {
            Optional<Shape> shape = shapes.stream().filter(s -> s.contains(e.getPoint())).findFirst();
            if (shape.isPresent()) {
                isDragged = true;
                currentShapeConsumer.accept(shape.get());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentAction.get() == ActionEnum.MOVE) {
            isDragged = false;
        }
        currentShapeConsumer.accept(null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && isDragged) {
            Optional<Shape> shape = currentShapeSupplier.get();
            shape.ifPresent(s -> s.move(e.getPoint()));
        }
        frame.repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}
