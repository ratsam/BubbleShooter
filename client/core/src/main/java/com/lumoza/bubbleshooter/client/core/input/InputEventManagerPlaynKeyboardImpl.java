package com.lumoza.bubbleshooter.client.core.input;

import playn.core.Keyboard;

/**
 * Simple input event manager that acts as PlayN keyboard listener and emits game events.
 */
public class InputEventManagerPlaynKeyboardImpl extends SimpleInputEventManager implements Keyboard.Listener {

    private final double angleDelta;

    /**
     * Construct PlayN Keyboard based input event manager with given cannon rotation angle.
     *
     * @param angleDelta cannon rotation angle
     */
    public InputEventManagerPlaynKeyboardImpl(double angleDelta) {
        this.angleDelta = angleDelta;
    }

    @Override
    public void onKeyDown(Keyboard.Event event) {
        switch (event.key()) {
            case LEFT:
                onCannonAngleChanged(-angleDelta);
                break;
            case RIGHT:
                onCannonAngleChanged(angleDelta);
                break;
            default:
                // Do not process other keys.
                break;
        }
    }

    @Override
    public void onKeyTyped(Keyboard.TypedEvent event) {
        // pass
    }

    @Override
    public void onKeyUp(Keyboard.Event event) {
        // pass
    }
}
