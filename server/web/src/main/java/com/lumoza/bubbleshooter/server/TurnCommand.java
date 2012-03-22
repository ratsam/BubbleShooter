package com.lumoza.bubbleshooter.server;

import java.io.Serializable;

/**
 * Command used to perform game turn.
 */
public class TurnCommand implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double angle;

    public Double getAngle() {
        return angle;
    }

    public void setAngle(Double angle) {
        this.angle = angle;
    }
}
