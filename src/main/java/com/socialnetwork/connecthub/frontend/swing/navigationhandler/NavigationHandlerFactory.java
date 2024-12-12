package com.socialnetwork.connecthub.frontend.swing.navigationhandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class to provide the appropriate NavigationHandler.
 * The factory returns an instance of the required NavigationHandler strategy.
 */
public class NavigationHandlerFactory {
    private static final Map<String, NavigationHandler> handlers = new HashMap<>();

    // Register basic handlers
    static {
        registerNavigationHandler("test", new TestNavigationHandler());
        registerNavigationHandler("final", new FinalNavigationHandler());
    }

    /**
     * Factory method to return a NavigationHandler based on the requested type.
     *
     * @param handlerType The type of the NavigationHandler (e.g., "test", "final").
     * @return An instance of NavigationHandler.
     */
    public static NavigationHandler getNavigationHandler(String handlerType) {
        NavigationHandler handler = handlers.get(handlerType.toLowerCase());
        if (handler == null) {
            throw new IllegalArgumentException("Unknown handler type: " + handlerType);
        }
        return handler;
    }

    /**
     * Register a new NavigationHandler strategy at runtime.
     *
     * @param handlerType The type of the NavigationHandler.
     * @param handler     The NavigationHandler instance.
     */
    public static void registerNavigationHandler(String handlerType, NavigationHandler handler) {
        handlers.put(handlerType.toLowerCase(), handler);
    }
}
