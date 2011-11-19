package com.lumoza.bubbleshooter.client.core;

import com.lumoza.bubbleshooter.client.core.input.CannonAngleChangedListener;
import com.lumoza.bubbleshooter.client.core.input.SimpleInputEventManager;
import playn.core.Game;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Keyboard;
import playn.core.PlayN;


/**
 * Core Bubble Shooter Game.
 */
public class BubbleShooter implements Game, Keyboard.Listener {

    /**
     * PlayN update frame rate.
     */
    private static final int UPDATE_FRAME_RATE = 25;
    private static final double CANNON_ANGLE_DELTA = 1.0d;

    /**
     * Temporary inner simple input event manager.
     */
    private SimpleInputEventManager inputEventManager = new SimpleInputEventManager();

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
        PlayN.keyboard().setListener(this);

        inputEventManager.setOnCannonAngleChangedListener(new CannonAngleChangedListener() {
            @Override
            public void onAngleChanged(double angle) {
                cannon.tilt(angle);
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

    @Override
    public void onKeyDown(Keyboard.Event event) {
        switch (event.key()) {
            case LEFT:
                inputEventManager.onCannonAngleChanged(-CANNON_ANGLE_DELTA);
                break;
            case RIGHT:
                inputEventManager.onCannonAngleChanged(CANNON_ANGLE_DELTA);
                break;
            default:
                // Do not process other keys.
                break;
        }
        cannonLayer.setRotation(degreesToRadians(cannon.getAngle()));
    }

    private float degreesToRadians(double angle) {
       return (float) Math.toRadians(angle);
    }

    @Override
    public void onKeyTyped(Keyboard.TypedEvent event) {
    }

    @Override
    public void onKeyUp(Keyboard.Event event) {
    }
}
