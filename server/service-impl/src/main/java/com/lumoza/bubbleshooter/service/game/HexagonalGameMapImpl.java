package com.lumoza.bubbleshooter.service.game;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Hexagonal game map implementation.
 */
public class HexagonalGameMapImpl implements GameMap {

    private final GameBubble[][] bubbles;

    /**
     * Constructor.
     *
     * @param rows rows count of the map
     * @param maxRowSize max row size to use
     */
    public HexagonalGameMapImpl(int rows, int maxRowSize) {
        this(generateEmptyMap(rows, maxRowSize));
    }

    private HexagonalGameMapImpl(GameBubble[][] bubbles) {
        this.bubbles = bubbles;
    }

    /**
     * Create {@link Iterator} over all existing bubbles in this map.
     *
     * @return iterator over all existing bubbles
     */
    @Override
    public Iterator<GameBubble> iterator() {
        final List<GameBubble> bubbleList = new ArrayList<GameBubble>();
        for (int row = 0, rc = bubbles.length; row < rc; row++) {
            for (int column = 0, cc = bubbles[row].length; column < cc; column++) {
                if (bubbles[row][column] != null) {
                    bubbleList.add(bubbles[row][column]);
                }
            }
        }
        return bubbleList.iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBubble findBubble(Position position) {
        return findBubble(position.getRow(), position.getColumn());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameBubble findBubble(int row, int column) {
        if (!isValidPosition(row, column)) {
            return null;
        }
        return bubbles[row][column];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameBubble> findNeighbors(Position position) {
        return findNeighbors(position.getRow(), position.getColumn());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameBubble> findNeighbors(int row, int column) {
        final List<GameBubble> neighbors = new ArrayList<GameBubble>();
        int odd = row % 2;
        neighbors.addAll(findBubbles(
            new Position(row - 1, column - 1 + odd),
            new Position(row - 1, column + odd),
            new Position(row, column - 1),
            new Position(row, column + 1),
            new Position(row + 1, column - 1 + odd),
            new Position(row + 1, column + odd)
        ));
        return neighbors;
    }

    private Collection<GameBubble> findBubbles(Position... positions) {
        final List<GameBubble> foundBubbles = new ArrayList<GameBubble>();
        for (Position position : positions) {
            if (!isValidPosition(position)) {
                continue;
            }

            final GameBubble bubble = bubbles[position.getRow()][position.getColumn()];
            if (bubble != null) {
                foundBubbles.add(bubble);
            }
        }
        return foundBubbles;
    }

    private boolean isValidPosition(Position position) {
        return isValidPosition(position.getRow(), position.getColumn());
    }

    private boolean isValidPosition(int row, int column) {
        return row >= 0 && column >= 0 && row < bubbles.length && column < bubbles[row].length;
    }

    @Override
    public int rowsCount() {
        return bubbles.length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasBubbles(int row) {
        for (int i = 0, c = bubbles[row].length; i < c; i++) {
            if (bubbles[row][i] != null) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int rowSize(int row) {
        if (row < 0 || row >= bubbles.length) {
            throw new IllegalArgumentException("Invalid 'row' value given.");
        }

        return bubbles[row].length;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBubble(GameBubble bubble) {
        bubbles[bubble.getPosition().getRow()][bubble.getPosition().getColumn()] = bubble;
    }

    private static GameBubble[][] generateEmptyMap(int rows, int maxRowSize) {
        final GameBubble[][] world = new GameBubble[rows][];
        for (int row = 0; row < rows; row++) {
            world[row] = new GameBubble[maxRowSize - row % 2];
        }
        return world;
    }
}
