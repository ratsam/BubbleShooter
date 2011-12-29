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

    private final PositionToCoordinatesConverter positionConverter;

    private final float bubbleSize;
    private final GameWorld gameWorld;

    /**
     * Constructor.
     *
     * @param positionConverter Position to Coordinates converter
     * @param bubbleSize bubble size to use
     * @param gameWorld game world to use
     */
    public NearestEmptyPositionResolver(PositionToCoordinatesConverter positionConverter, float bubbleSize, GameWorld gameWorld) {
        this.positionConverter = positionConverter;
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
            final float candidatePosY = positionConverter.yCoordinateFromRow(row);
            if (Math.abs(state.getBodyPositionY() - candidatePosY) > state.getMinDistance()) { // Kind of optimisation
                continue;
            }

            processColumns(row, state);
        }
    }

    private void processColumns(int row, State state) {
        for (int column = 0, rowSize = gameWorld.rowSize(row); column < rowSize - (row % 2); column++) {
            final float candidatePosX = positionConverter.xCoordinateFromPosition(row, column);
            if (Math.abs(state.getBodyPositionX() - candidatePosX) < state.getMinDistance()) { // Kind of optimisation
                processColumn(row, column, state);
            }
        }
    }

    private double distance(State state, int targetRow, int targetColumn) {
        final Vec2 coordinates = positionConverter.convert(targetRow, targetColumn);
        return distance(state.getBodyPositionX(), state.getBodyPositionY(), coordinates.x, coordinates.y);
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
