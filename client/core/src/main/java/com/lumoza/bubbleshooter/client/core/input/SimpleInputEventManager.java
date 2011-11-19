package com.lumoza.bubbleshooter.client.core.input;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple InputEventManager implementation.
 */
public class SimpleInputEventManager implements InputEventManager {

    private final List<CannonAngleChangedListener> cannonAngleChangedListeners;
    private final List<FireListener> fireListeners;

    /**
     * Default constructor.
     */
    public SimpleInputEventManager() {
        cannonAngleChangedListeners = new ArrayList<CannonAngleChangedListener>(1);
        fireListeners = new ArrayList<FireListener>(1);
    }

    /**
     * Dispatch cannon angle changed event.
     *
     * @param angle suggested cannon angle
     */
    public void onCannonAngleChanged(double angle) {
        for (CannonAngleChangedListener listener : cannonAngleChangedListeners) {
            listener.onAngleChanged(angle);
        }
    }

    /**
     * Dispatch cannon fire event.
     */
    public void onFire() {
        for (FireListener listener : fireListeners) {
            listener.onFire();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnCannonAngleChangedListener(@Nullable CannonAngleChangedListener listener) {
        cannonAngleChangedListeners.clear();
        if (listener != null) {
            addOnCannonAngleChangedListener(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOnCannonAngleChangedListener(@Nonnull CannonAngleChangedListener listener) {
        cannonAngleChangedListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOnFireListener(@Nullable FireListener listener) {
        fireListeners.clear();
        if (listener != null) {
            addOnFireListener(listener);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOnFireListener(@Nonnull FireListener listener) {
        fireListeners.add(listener);
    }
}
