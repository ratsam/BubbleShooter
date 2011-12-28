package com.lumoza.bubbleshooter.service;

import com.lumoza.bubbleshooter.service.game.GameWorld;
import com.lumoza.bubbleshooter.service.game.Position;
import org.jbox2d.common.Vec2;

import javax.annotation.concurrent.NotThreadSafe;

/**
 * Temporary, not thread-safe nearest empty landing position resolver.
 * Need to be reworked.
 */
@NotThreadSafe
public class NearestEmptyPositionResolver {

    private static final float TRIANGLE_HEIGHT_MOD = (float) (Math.sqrt(3) / 2);

    private final float bubbleSize;
    private final GameWorld gameWorld;

    private double minDistance;
    private Position matchedPosition = null;

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
        // TODO: replace with algorithm that provides better performance
        // BTW coordinates for all row/column pairs can be precalculated.
        minDistance = bubbleSize * 1.5;
        matchedPosition = null;

        doResolveForCoordinates(bodyPositionX, bodyPositionY);

        return matchedPosition;
    }

    private void doResolveForCoordinates(float bodyPositionX, float bodyPositionY) {
        for (int row = 0, rowsCount = gameWorld.rowsCount(); row < rowsCount; row++) {
            if (row > 0) {
                if (!gameWorld.hasBubbles(row - 1)) { // Kind of optimisation
                    break;
                }
            }
            final float candidatePosY = getBubbleYCoordinate(row);
            if (Math.abs(bodyPositionY - candidatePosY) > minDistance) { // Kind of optimisation
                continue;
            }

            processColumns(row, candidatePosY, bodyPositionX, bodyPositionY);
        }
    }

    private void processColumns(int row, float candidatePosY, float bodyPositionX, float bodyPositionY) {
        for (int column = 0, rowSize = gameWorld.rowSize(row); column < rowSize - (row % 2); column++) {
            final float candidatePosX = getBubbleXCoordinate(row, column);
            if (Math.abs(bodyPositionX - candidatePosX) < minDistance) { // Kind of optimisation
                final double candidateDistance = distance(bodyPositionX, bodyPositionY, candidatePosX, candidatePosY);
                if (candidateDistance < minDistance && gameWorld.isLandingPosition(row, column)) {
                    matchedPosition = new Position(row, column);
                    minDistance = candidateDistance;
                }
            }
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

    private double distance(double x1, double y1, double x2, double y2) {
        double xx = Math.pow(x1 - x2, 2);
        double yy = Math.pow(y1 - y2, 2);
        double sqrDistance = Math.abs(xx - yy);
        return Math.sqrt(sqrDistance);
    }
}
