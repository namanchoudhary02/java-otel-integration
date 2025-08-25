package com.simpl.java_otel_integration.logger;

import java.text.MessageFormat;
import java.util.logging.Level;

/**
 * Simple logger for the OpenTelemetry client
 */
public class Logger {
    // Use Java's built-in logging
    private static final java.util.logging.Logger javaLogger =
            java.util.logging.Logger.getLogger("GoTel");

    /**
     * Log a debug message
     *
     * @param message Message format string
     * @param args    Message arguments for formatting
     */
    public static void debug(String message, Object... args) {
        log(Level.FINE, message, args);
    }

    /**
     * Log an info message
     *
     * @param message Message format string
     * @param args    Message arguments for formatting
     */
    public static void info(String message, Object... args) {
        log(Level.INFO, message, args);
    }

    /**
     * Log a warning message
     *
     * @param message Message format string
     * @param args    Message arguments for formatting
     */
    public static void warn(String message, Object... args) {
        log(Level.WARNING, message, args);
    }

    /**
     * Log an error message
     *
     * @param message Message format string
     * @param args    Message arguments for formatting
     */
    public static void error(String message, Object args) {
        Throwable throwable = extractThrowable(args instanceof Object[] ? (Object[]) args : new Object[]{args});

        if (throwable != null) {
            // Log with exception
            javaLogger.log(Level.SEVERE, formatMessage(message, args), throwable);
        } else {
            // Log without exception
            log(Level.SEVERE, message, args);
        }
    }

    /**
     * Log a message at the specified level
     *
     * @param level   Logging level
     * @param message Message format string
     * @param args    Message arguments for formatting
     */
    private static void log(Level level, String message, Object... args) {
        if (javaLogger.isLoggable(level)) {
            javaLogger.log(level, formatMessage(message, args));
        }
    }

    /**
     * Format a message with arguments
     *
     * @param message Message format string
     * @param args    Message arguments
     * @return Formatted message
     */
    private static String formatMessage(String message, Object... args) {
        if (args == null || args.length == 0) {
            return message;
        }

        try {
            return MessageFormat.format(message, args);
        } catch (Exception e) {
            return message + " [Error formatting message: " + e.getMessage() + "]";
        }
    }

    /**
     * Extract a Throwable from the argument list if present
     *
     * @param args Arguments to check
     * @return Throwable if found, null otherwise
     */
    private static Throwable extractThrowable(Object[] args) {
        if (args != null && args.length > 0) {
            Object lastArg = args[args.length - 1];
            if (lastArg instanceof Throwable) {
                return (Throwable) lastArg;
            }
        }
        return null;
    }

    /**
     * Configure the logger's level
     *
     * @param enableDebug If true, enable debug logging
     */
    public static void configure(boolean enableDebug) {
        javaLogger.setLevel(enableDebug ? Level.FINE : Level.INFO);
    }
}