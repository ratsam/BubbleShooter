package com.lumoza.bubbleshooter.client.java;

import com.lumoza.bubbleshooter.client.core.BubbleShooter;
import playn.core.PlayN;
import playn.java.JavaPlatform;

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
     *
     * @param args run arguments
     */
    public static void main(final String[] args) {
        JavaPlatform platform = JavaPlatform.register();
        platform.assetManager().setPathPrefix(PATH_PREFIX);
        PlayN.run(new BubbleShooter());
    }
}
