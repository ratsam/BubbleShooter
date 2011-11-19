package com.lumoza.bubbleshooter.client.core;

/**
 * Represents game cannon.
 */
public class Cannon {

    private static final double MIN_ANGLE = -90;
    private static final double MAX_ANGLE = 90;

    private double angle = 0; // XXX: be VERY careful with doubles.

    /**
     * Tilt cannon left for given angle (in degrees).
     *
     * @param delta angle to tilt left
     */
    public void tiltLeft(double delta) {
        tilt(-delta);
    }

    /**
     * Tilt cannon right for given angle (in degrees).
     *
     * @param delta angle to tilt right
     */
    public void tiltRight(double delta) {
        tilt(delta);
    }

    /**
     * Tilt cannon for given angle (in degrees).
     *
     * @param delta angle to tilt
     */
    public void tilt(double delta) {
        setAngle(getAllowedAngleByDelta(delta));
    }

    private double getAllowedAngleByDelta(double delta) {
        double newAngle = angle + delta;
        newAngle = Math.max(MIN_ANGLE, newAngle);
        newAngle = Math.min(MAX_ANGLE, newAngle);
        return newAngle;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
