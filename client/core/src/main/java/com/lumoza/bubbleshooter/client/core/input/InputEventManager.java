package com.lumoza.bubbleshooter.client.core.input;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Abstraction layer between Game logic and actual input events processing.
 */
public interface InputEventManager {

    /**
     * Set listener for cannon angle changed event.
     * Provide <code>null</code> to disable event processing.
     *
     * @param listener listener to set
     */
    void setOnCannonAngleChangedListener(@Nullable CannonAngleChangedListener listener);

    /**
     * Add listener for cannon angle changed event to the end of listeners queue.
     *
     * @param listener listener to add
     */
    void addOnCannonAngleChangedListener(@Nonnull CannonAngleChangedListener listener);

    /**
     * Set listener for cannon 'fire' event.
     * Provide <code>null</code> to disable event processing.
     *
     * @param listener listener to set
     */
    void setOnFireListener(@Nullable FireListener listener);

    /**
     * Add listener for cannon 'fire' event to the end of listeners queue.
     *
     * @param listener listener to add
     */
    void addOnFireListener(@Nonnull FireListener listener);
}
