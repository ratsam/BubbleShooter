package com.lumoza.bubbleshooter.client.core;

import com.lumoza.bubbleshooter.client.core.input.CannonAngleChangedListener;
import com.lumoza.bubbleshooter.client.core.input.InputEventManagerPlaynKeyboardImpl;
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
    private static final double CANNON_ANGLE_DELTA = 1.0d;

    /**
     * Temporary inner input event manager instantiation.
     */
    private InputEventManagerPlaynKeyboardImpl inputEventManager = new InputEventManagerPlaynKeyboardImpl(CANNON_ANGLE_DELTA);

    private Cannon cannon;
    private ImageLayer cannonLayer;


    /**
     * Init method.
     */
    @Override
    public final void init() {
        // create and add background image layer
        Image bgImage = PlayN.assetManager().getImage("images/bg.png");
        ImageLayer bgLayer = PlayN.graphics().createImageLayer(bgImage);
        PlayN.graphics().rootLayer().add(bgLayer);

        createCannonLayer(bgImage);

        cannon = new Cannon();
        setListeners();
    }

    private void createCannonLayer(Image bgImage) {
        Image cannonImage = PlayN.assetManager().getImage("images/cannon.png");
        cannonLayer = PlayN.graphics().createImageLayer(cannonImage);
        cannonLayer.setOrigin(cannonImage.width() / 2, cannonImage.height());
        cannonLayer.setTranslation(
            bgImage.width() / 2 - cannonImage.width() / 2,
            bgImage.height() - cannonImage.height() / 2
        );
        PlayN.graphics().rootLayer().add(cannonLayer);
    }

    private void setListeners() {
        PlayN.keyboard().setListener(inputEventManager);

        inputEventManager.setOnCannonAngleChangedListener(new CannonAngleChangedListener() {
            @Override
            public void onAngleChanged(double angle) {
                cannon.tilt(angle);
                cannonLayer.setRotation(degreesToRadians(cannon.getAngle()));
            }
        });
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

    private float degreesToRadians(double angle) {
       return (float) Math.toRadians(angle);
    }
}
