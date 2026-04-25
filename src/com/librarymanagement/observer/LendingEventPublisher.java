package com.librarymanagement.observer;

import java.util.ArrayList;
import java.util.List;

public class LendingEventPublisher {
    private final List<LendingObserver> observers = new ArrayList<>();

    public void registerObserver(LendingObserver observer) {
        observers.add(observer);
    }

    public void publish(LendingEvent event) {
        for (LendingObserver observer : observers) {
            observer.onLendingEvent(event);
        }
    }
}

