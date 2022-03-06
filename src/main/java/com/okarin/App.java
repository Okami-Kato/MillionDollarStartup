package com.okarin;

import com.okarin.action.ActionEnum;
import com.okarin.domain.Shape;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

@SpringBootApplication
public class App extends JFrame {
    private JPanel rootPanel;
    private JPanel canvas;

    private JButton segmentButton;
    private JButton rayButton;
    private JButton moveButton;

    private JSlider thicknessSlider;

    private JSlider frameRedSlider;
    private JSlider frameGreenSlider;
    private JSlider frameBlueSlider;

    private JSlider fillRedSlider;
    private JSlider fillGreenSlider;
    private JSlider fillBlueSlider;
    private JButton lineButton;

    private Optional<Shape> currentShape = Optional.empty();

    private ActionEnum action;

    private final List<Shape> shapes = new ArrayList<>();

    @Autowired
    private List<MouseAdapter> adapters;

    public static void main(String[] args) {
        var ctx = new SpringApplicationBuilder(App.class)
                .headless(false).run(args);

        EventQueue.invokeLater(() -> {
            var ex = ctx.getBean(App.class);
            ex.setVisible(true);
        });
    }

    @Bean
    public List<Shape> shapes() {
        return shapes;
    }

    @Bean
    public Supplier<Optional<Shape>>
    currentShapeSupplier() {
        return () -> currentShape;
    }

    @Bean
    public Consumer<Shape> currentShapeConsumer() {
        return s -> currentShape = Optional.ofNullable(s);
    }

    @Bean
    public Supplier<ActionEnum> currentActionSupplier() {
        return () -> action;
    }

    @Bean
    public Supplier<Integer> frameThicknessSupplier() {
        return () -> thicknessSlider.getValue();
    }

    @Bean
    @Qualifier("frame")
    public Supplier<Color> frameColorSupplier() {
        return () -> new Color(frameRedSlider.getValue(), frameGreenSlider.getValue(), frameBlueSlider.getValue());
    }

    @Bean
    @Qualifier("fill")
    public Supplier<Color> fillColorSupplier() {
        return () -> new Color(fillRedSlider.getValue(), fillGreenSlider.getValue(), fillBlueSlider.getValue());
    }

    public App() {
        super("Just Draw It");
        setContentPane(rootPanel);
        setSize(1000, 1000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @PostConstruct
    private void setUpGUI() {
        segmentButton.addActionListener(e -> action = ActionEnum.SEGMENT);
        rayButton.addActionListener(e -> action = ActionEnum.RAY);
        lineButton.addActionListener(e -> action = ActionEnum.LINE);
        moveButton.addActionListener(e -> action = ActionEnum.MOVE);

        frameRedSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), "Red"));
        frameGreenSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), "Green"));
        frameBlueSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Blue"));

        fillRedSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.RED), "Red"));
        fillGreenSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GREEN), "Green"));
        fillBlueSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLUE), "Blue"));

        thicknessSlider.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Thickness"));

        adapters.forEach(a -> {
            canvas.addMouseMotionListener(a);
            canvas.addMouseListener(a);
        });
    }

    private void createUIComponents() {
        canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for (Shape s : shapes)
                    s.draw(g2d);
            }
        };
    }
}


