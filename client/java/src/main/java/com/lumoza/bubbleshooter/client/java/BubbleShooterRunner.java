package com.lumoza.bubbleshooter.client.java;

import com.lumoza.bubbleshooter.client.core.BubbleShooter;
import playn.core.Game;
import playn.core.PlayN;
import playn.java.JavaPlatform;

/**
 * Java platform runner for Bubble Shooter.
 */
public class BubbleShooterRunner {

    private final String pathPrefix;

    /**
     * Create Bubble Shooter Java runner with given (resources) path prefix.
     *
     * @param pathPrefix path prefix
     */
    public BubbleShooterRunner(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }

    /**
     * Init PlayN Java platform.
     */
    public void initPlatform() {
        JavaPlatform platform = JavaPlatform.register();
        platform.assetManager().setPathPrefix(pathPrefix);
    }

    /**
     * Start the game.
     */
    public void run() {
        PlayN.run(createGame());
    }

    private Game createGame() {
        final BubbleShooter game = new BubbleShooter();
        return game;
    }
}
