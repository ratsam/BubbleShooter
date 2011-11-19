package com.lumoza.bubbleshooter.client.java;

/**
 * Java client runner.
 */
public final class BubbleShooterJava {

    /**
     * Static path prefix.
     */
    private static final String PATH_PREFIX =
            "src/main/java/com/lumoza/bubbleshooter/client/resources";

    /**
     * Private constructor for utility class.
     */
    private BubbleShooterJava() {

    }

    /**
     * Bubble Shooter Java program entry point.
     *
     * @param args run arguments
     */
    public static void main(final String[] args) {
        final BubbleShooterRunner runner = new BubbleShooterRunner(PATH_PREFIX);
        runner.initPlatform();
        runner.run();
    }
}
