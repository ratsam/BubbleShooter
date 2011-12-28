package com.lumoza.bubbleshooter.service.game;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Game bubble.
 */
public class GameBubble implements Serializable {

    private static final long serialVersionUID = 1L;

    private int color;
    private Position position;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        return equals((GameBubble) obj);
    }

    private boolean equals(GameBubble that) {
        return new EqualsBuilder()
                .append(this.color, that.color)
                .append(this.position, that.position)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(color)
                .append(position)
                .toHashCode();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
