package com.okarin.event;

import org.springframework.context.ApplicationEvent;

public class RepaintEvent extends ApplicationEvent {
    public RepaintEvent(Object source) {
        super(source);
    }
}
