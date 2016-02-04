package io.sytac.resumator;

import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Handler for bridging Jersey's JUL logging to SLF4J.
 */
public class LoggingBridge {
    public LoggingBridge() {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
    }
}
