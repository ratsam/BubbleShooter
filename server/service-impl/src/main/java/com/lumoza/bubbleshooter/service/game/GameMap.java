package com.lumoza.bubbleshooter.service.game;

import java.util.Collection;

/**
 * Game map.
 */
public interface GameMap extends Iterable<GameBubble> {

    /**
     * Find bubble with given position.
     *
     * @param position position to find
     * @return bubble if exists otherwise null
     */
    GameBubble findBubble(Position position);

    /**
     * Find bubble with position that matches given row and column.
     *
     * @param row row to match against
     * @param column column to match against
     * @return bubble if exists otherwise null
     */
    GameBubble findBubble(int row, int column);

    /**
     * Find all neighbor bubbles for given position.
     *
     * @param position position to use
     * @return collection with all neighbor bubbles
     */
    Collection<GameBubble> findNeighbors(Position position);

    /**
     * Find all neighbor bubbles for position with given row and column.
     *
     * @param row row to use
     * @param column column to use
     * @return collection with all neighbor bubbles
     */
    Collection<GameBubble> findNeighbors(int row, int column);

    /**
     * Return rows count.
     *
     * @return rows count
     */
    int rowsCount();

    /**
     * Check if given row has at least one bubble.
     *
     * @param row row number to check
     * @return true if row with given number has at least one bubble otherwise false
     */
    boolean hasBubbles(int row);

    /**
     * Return number of columns for given row.
     * Throws {@link IllegalArgumentException} if row does not exist.
     *
     * @param row row number
     * @return number of columns for given row
     */
    int rowSize(int row);

    /**
     * Add new bubble to the map.
     *
     * @param bubble bubble to add
     */
    void addBubble(GameBubble bubble);
}
