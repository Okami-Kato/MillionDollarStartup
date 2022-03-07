package com.okarin.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.swing.JFrame;

@Component
public class RepaintEventListener implements ApplicationListener<RepaintEvent> {
    @Autowired
    private JFrame canvas;

    @Override
    public void onApplicationEvent(RepaintEvent event) {
        canvas.repaint();
    }
}
