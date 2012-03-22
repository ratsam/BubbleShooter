package com.lumoza.bubbleshooter.service;

import com.lumoza.bubbleshooter.service.game.ContactInfo;

import java.util.List;

/**
 * Glue between Game and Physic objects.
 */
public interface GamePhysicProcessor {

    /**
     * Reset.
     * To be used from "testbeds" generally.
     */
    void reset();

    /**
     * Create fire bubble (if not exists yet) and launches it through physic World.
     *
     * @param degreesAngle angle (in degrees) to fire
     */
    void fire(double degreesAngle);

    /**
     * Invoke physics calculation (in loop) until fire bubble is stopped.
     */
    void runPhysics();

    /**
     * Process contacts history and return copy of processed contacts list.
     *
     * @return copy of processed contacts list.
     */
    List<ContactInfo> processContactsHistory();
}
