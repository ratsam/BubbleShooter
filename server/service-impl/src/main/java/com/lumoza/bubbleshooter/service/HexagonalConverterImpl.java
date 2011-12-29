package com.lumoza.bubbleshooter.service;

import com.lumoza.bubbleshooter.service.game.Position;
import org.jbox2d.common.Vec2;

/**
 * Implementation of {@link PositionToCoordinatesConverter} for hexagonal game world.
 */
public class HexagonalConverterImpl implements PositionToCoordinatesConverter {

    private static final float TRIANGLE_HEIGHT_MOD = (float) (Math.sqrt(3) / 2);

    private final int rowsCount;
    private final int maxRowSize;
    private final float bubbleRadius;

    /**
     * Constructor.
     *
     * @param rowsCount rows count
     * @param maxRowSize max row size (for even rows)
     * @param bubbleRadius bubble radius to use
     */
    public HexagonalConverterImpl(int rowsCount, int maxRowSize, float bubbleRadius) {
        this.rowsCount = rowsCount;
        this.maxRowSize = maxRowSize;
        this.bubbleRadius = bubbleRadius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec2 convert(Position position) {
        return convert(position.getRow(), position.getColumn());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Vec2 convert(int row, int column) {
        return new Vec2(xCoordinateFromPosition(row, column), yCoordinateFromRow(row));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float yCoordinateFromRow(int row) {
        return ((rowsCount - row) * TRIANGLE_HEIGHT_MOD - 0.5f) * bubbleRadius * 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float xCoordinateFromPosition(Position position) {
        return xCoordinateFromPosition(position.getRow(), position.getColumn());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public float xCoordinateFromPosition(int row, int column) {
        float posX = (maxRowSize - (maxRowSize - column) + 0.5f) * bubbleRadius * 2; // TODO: fixme
        if (row % 2 != 0) {
            posX += bubbleRadius;
        }

        return posX;
    }
}
