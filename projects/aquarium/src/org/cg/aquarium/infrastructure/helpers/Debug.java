package org.cg.aquarium.infrastructure.helpers;

import org.cg.aquarium.infrastructure.Environment;

/**
 * Debug helper.
 *
 * Prints debugging messages to a output buffer if {@code Environment.debugging}
 * is set to true.
 *
 * @author ldavid
 */
public abstract class Debug {

    public static void info(String message) {
        write(message, "Info");
    }

    public static void warning(String message) {
        write(message, "Warning");
    }

    public static void error(String message) {
        write(message, "Error");
    }

    protected static void write(String message, String title) {
        if (Environment.getEnvironment().isDebugging()) {
            System.out.println(String.format("%s: %s", title, message));
        }
    }
}
