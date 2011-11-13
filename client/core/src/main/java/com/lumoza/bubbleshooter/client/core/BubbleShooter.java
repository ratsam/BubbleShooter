package com.lumoza.bubbleshooter.client.core;

import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.PlayN;

/**
 * Core Bubble Shooter Game.
 */
public class BubbleShooter implements Game {

    /**
     * PlayN update frame rate.
     */
    private static final int UPDATE_FRAME_RATE = 25;

    /**
     * Init method.
     */
    @Override
    public final void init() {
        // create and add background image layer
        Image bgImage = PlayN.assetManager().getImage("images/bg.png");
        ImageLayer bgLayer = PlayN.graphics().createImageLayer(bgImage);
        PlayN.graphics().rootLayer().add(bgLayer);
    }

    /**
     * Perform paint with specified alpha.
     *
     * @param alpha paint alpha
     */
    @Override
    public final void paint(final float alpha) {
        // the background automatically paints itself,
        // so no need to do anything here!
    }

    /**
     * Perform update.
     *
     * @param delta time spent from last update
     */
    @Override
    public final void update(final float delta) {
    }

    /**
     * Return update rate.
     *
     * @return frame update rate
     */
    @Override
    public final int updateRate() {
        return UPDATE_FRAME_RATE;
    }
}
