package com.lumoza.bubbleshooter.service;

import com.lumoza.bubbleshooter.service.game.Position;
import org.jbox2d.common.Vec2;

/**
 * Position (Game domain object) to Coordinates (Physics domain object) converter.
 */
public interface PositionToCoordinatesConverter {

    /**
     * Convert given game Position to Vec2 coordinates.
     *
     * @param position Position to convert
     * @return coordinates for given position
     */
    Vec2 convert(Position position);

    /**
     * Convert given game row and column to Vec2 coordinates.
     *
     * @param row row to convert
     * @param column column to convert
     * @return coordinates for given row and column
     */
    Vec2 convert(int row, int column);

    /**
     * Convert given game row to physic Y coordinate.
     *
     * @param row row to convert
     * @return Y coordinate for given game row
     */
    float yCoordinateFromRow(int row);

    /**
     * Convert given game position to X coordinate.
     *
     * @param position position to convert
     * @return X coordinate for given game position
     */
    float xCoordinateFromPosition(Position position);

    /**
     * Convert given game row and column to X coordinate.
     *
     * @param row game row to convert
     * @param column game column to convert
     * @return X coordinate for given game row and column
     */
    float xCoordinateFromPosition(int row, int column);
}
