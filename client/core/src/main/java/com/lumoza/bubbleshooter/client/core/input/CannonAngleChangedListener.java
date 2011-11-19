package com.lumoza.bubbleshooter.client.core.input;

/**
 * Listener for cannon angle changed event.
 *
 * @see InputEventManager
 */
public interface CannonAngleChangedListener {

    /**
     * Process angle changed event.
     *
     * @param angle suggested angle value
     */
    void onAngleChanged(double angle);
}
