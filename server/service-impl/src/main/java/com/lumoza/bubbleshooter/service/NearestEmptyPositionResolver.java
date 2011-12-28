package com.lumoza.bubbleshooter.service;

import com.lumoza.bubbleshooter.service.game.GameWorld;
import com.lumoza.bubbleshooter.service.game.Position;
import org.jbox2d.common.Vec2;

import java.io.Serializable;

/**
 * Helper class for resolving nearest available landing position.
 * Landing position is an empty position that either has at least one neighbor bubble or located at zero row.
 */
public class NearestEmptyPositionResolver {

    private static final float TRIANGLE_HEIGHT_MOD = (float) (Math.sqrt(3) / 2);

    private final float bubbleSize;
    private final GameWorld gameWorld;

    /**
     * Constructor.
     *
     * @param bubbleSize bubble size to use
     * @param gameWorld game world to use
     */
    public NearestEmptyPositionResolver(float bubbleSize, GameWorld gameWorld) {
        this.bubbleSize = bubbleSize;
        this.gameWorld = gameWorld;
    }

    /**
     * Resolve landing position nearest to given coordinates.
     *
     * @param bodyPositionX X position to use
     * @param bodyPositionY Y position to use
     * @return nearest landing position
     */
    public Position resolveForCoordinates(float bodyPositionX, float bodyPositionY) {
        final State state = new State(bodyPositionX, bodyPositionY);
        state.setMinDistance(bubbleSize * 1.5);

        doResolveForCoordinates(state);

        return state.getMatchedPosition();
    }

    private void doResolveForCoordinates(State state) {
        // TODO: replace with algorithm that provides better performance
        // BTW coordinates for all row/column pairs can be precalculated.
        for (int row = 0, rowsCount = gameWorld.rowsCount(); row < rowsCount; row++) {
            if (row > 0 && !gameWorld.hasBubbles(row - 1)) { // Kind of optimisation
                break;
            }
            final float candidatePosY = getBubbleYCoordinate(row);
            if (Math.abs(state.getBodyPositionY() - candidatePosY) > state.getMinDistance()) { // Kind of optimisation
                continue;
            }

            processColumns(row, state);
        }
    }

    private Vec2 getBubbleCoordinates(Position position) {
        return getBubbleCoordinates(position.getRow(), position.getColumn());
    }

    /**
     * Bubbles should be hexagonal packed (@see Circle Packing for more details).
     * Talking short, there must be the same distance between centers of any two nearby circles.
     *
     * @param row bubble row
     * @param column bubble column
     * @return Vec2 instance with bubble position
     */
    private Vec2 getBubbleCoordinates(int row, int column) {
        return new Vec2(getBubbleXCoordinate(row, column), getBubbleYCoordinate(row));
    }

    private float getBubbleYCoordinate(int row) {
        return ((gameWorld.rowsCount() - row) * TRIANGLE_HEIGHT_MOD - 0.5f) * bubbleSize;
    }

    private float getBubbleXCoordinate(int row, int column) {
        int rowSize = gameWorld.rowSize(row);
        float posX = (rowSize - (rowSize - column) + 0.5f) * bubbleSize;
        if (row % 2 != 0) {
            posX += bubbleSize / 2;
        }

        return posX;
    }

    private void processColumns(int row, State state) {
        for (int column = 0, rowSize = gameWorld.rowSize(row); column < rowSize - (row % 2); column++) {
            final float candidatePosX = getBubbleXCoordinate(row, column);
            if (Math.abs(state.getBodyPositionX() - candidatePosX) < state.getMinDistance()) { // Kind of optimisation
                processColumn(row, column, state);
            }
        }
    }

    private double distance(State state, int targetRow, int targetColumn) {
        final float targetPositionX = getBubbleXCoordinate(targetRow, targetColumn);
        final float targetPositionY = getBubbleYCoordinate(targetRow);
        return distance(state.getBodyPositionX(), state.getBodyPositionY(), targetPositionX, targetPositionY);
    }

    private void processColumn(int row, int column, State state) {
        final double candidateDistance = distance(state, row, column);
        if (candidateDistance < state.getMinDistance() && gameWorld.isLandingPosition(row, column)) {
            state.setMatchedPosition(new Position(row, column));
            state.setMinDistance(candidateDistance);
        }
    }

    private double distance(double x1, double y1, double x2, double y2) {
        double xx = Math.pow(x1 - x2, 2);
        double yy = Math.pow(y1 - y2, 2);
        double sqrDistance = Math.abs(xx - yy);
        return Math.sqrt(sqrDistance);
    }

    /**
     * Inner class to store state.
     */
    private static class State implements Serializable {

        private static final long serialVersionUID = 1L;

        private final float bodyPositionX;
        private final float bodyPositionY;

        private double minDistance;
        private Position matchedPosition;

        private State(float bodyPositionX, float bodyPositionY) {
            this.bodyPositionX = bodyPositionX;
            this.bodyPositionY = bodyPositionY;
        }

        public float getBodyPositionX() {
            return bodyPositionX;
        }

        public float getBodyPositionY() {
            return bodyPositionY;
        }

        public double getMinDistance() {
            return minDistance;
        }

        public void setMinDistance(double minDistance) {
            this.minDistance = minDistance;
        }

        public Position getMatchedPosition() {
            return matchedPosition;
        }

        public void setMatchedPosition(Position matchedPosition) {
            this.matchedPosition = matchedPosition;
        }
    }
}
