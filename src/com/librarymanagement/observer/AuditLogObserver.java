package com.librarymanagement.observer;

import java.util.logging.Logger;

public class AuditLogObserver implements LendingObserver {
    private static final Logger LOGGER = Logger.getLogger(AuditLogObserver.class.getName());

    @Override
    public void onLendingEvent(LendingEvent event) {
        if (event.getEventType() == LendingEventType.CHECKOUT) {
            LOGGER.info(String.format("AUDIT: Patron %s checked out %s, due on %s",
                    event.getPatronId(), event.getIsbn(), event.getDueDate()));
        } else {
            LOGGER.info(String.format("AUDIT: Patron %s returned %s",
                    event.getPatronId(), event.getIsbn()));
        }
    }
}

