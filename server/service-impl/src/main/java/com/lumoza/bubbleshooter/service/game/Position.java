package com.lumoza.bubbleshooter.service.game;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.io.Serializable;

/**
 * Bubble position.
 */
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    private int row;
    private int column;

    /**
     * Constructor.
     *
     * @param row row to use
     * @param column column to use
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

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

        return equals((Position) obj);
    }

    private boolean equals(Position other) {
        return new EqualsBuilder()
                .append(this.row, other.row)
                .append(this.column, other.column)
                .isEquals();
    }

    /**
     * {@inheritDoc}
     */
    public int hashCode() {
        return new HashCodeBuilder()
                .append(row)
                .append(column)
                .toHashCode();
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
