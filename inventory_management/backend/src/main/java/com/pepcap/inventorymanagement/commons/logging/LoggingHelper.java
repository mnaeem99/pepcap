package com.pepcap.inventorymanagement.commons.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingHelper {

    public Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}

