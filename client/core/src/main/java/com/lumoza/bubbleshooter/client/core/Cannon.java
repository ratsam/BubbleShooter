package com.lumoza.bubbleshooter.client.core;

public class Cannon {
    public double angle = 0;
    private final double MIN_ANGLE = -90;
    private final double MAX_ANGLE = 90;

    public void tiltLeft(double delta) {
        setAngle(getAllowedAngleByDelta(-delta));
    }

    public void tiltRight(double delta) {
        setAngle(getAllowedAngleByDelta(delta));
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getAllowedAngleByDelta(double delta) {
        double newAngle = angle + delta;
        newAngle = Math.max(MIN_ANGLE, newAngle);
        newAngle = Math.min(MAX_ANGLE, newAngle);
        return newAngle;
    }

}
