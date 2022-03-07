package com.okarin.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RepaintEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publish() {
        applicationEventPublisher.publishEvent(new RepaintEvent(this));
    }
}
