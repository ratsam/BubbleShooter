package com.lumoza.bubbleshooter.service.game;

import java.io.Serializable;

/**
 * Fire bubble contact info.
 */
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Fire bubble contact info type.
     * Either bound or another bubble.
     */
    public static enum ContactType {

        /**
         * Contact info type that indicates that fire bubble contacted with game world bounds.
         */
        BOUNDS,

        /**
         * Contact info type that indicates that fire bubble contacted with another bubble.
         */
        BUBBLE
    }

    private float posX;
    private float posY;
    private ContactType type;
    private boolean finalContact;

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    public boolean isFinalContact() {
        return finalContact;
    }

    public void setFinalContact(boolean finalContact) {
        this.finalContact = finalContact;
    }
}
