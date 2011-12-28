package com.lumoza.bubbleshooter.service.game;

/**
 * Game world.
 */
public interface GameWorld {

    /**
     * Add listener for bubble creation event.
     * @see {@link BubbleCreationListener}.
     *
     * @param listener listener to set
     */
    void addBubbleCreationListener(BubbleCreationListener listener);

    /**
     * Add listener for bubble destroying event.
     * @see {@link BubbleDestroyingListener}.
     *
     * @param listener listener to set
     */
    void addBubbleDestroyingListener(BubbleDestroyingListener listener);

    /**
     * Check if given position can be used as "landing" position.
     * "Landing" position is position that is empty but have at least one neighbor or in zero row.
     *
     * @param position position to check
     * @return true if position can be used as "landing" otherwise false
     */
    boolean isLandingPosition(Position position);

    /**
     * Check if position with given row and column can be used as "landing" position.
     * "Landing" position is position that is empty but have at least one neighbor or in zero row.
     *
     * @param row row of position to check
     * @param column column of position to check
     * @return true if position with given row and column can be used as "landing" otherwise false
     */
    boolean isLandingPosition(int row, int column);

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
     * Process fire bubble landing in given position.
     *
     * @param position position to use
     */
    void processFireBubble(Position position);

    /**
     * Return {@link Iterable} with all existing bubbles.
     *
     * @return Iterable with all existing bubbles
     */
    Iterable<GameBubble> getBubbles();

    /**
     * Lambda-interface to receive bubble creation events.
     */
    public interface BubbleCreationListener {

        /**
         * Method to be invoked on new bubble creation.
         *
         * @param gameBubble newly created bubble
         */
        void onBubbleCreated(GameBubble gameBubble);
    }

    /**
     * Lambda-interface to receive bubble destroying events.
     */
    public interface BubbleDestroyingListener {

        /**
         * Method to be invoked on bubble destroying.
         *
         * @param gameBubble bubble to be destroyed
         */
        void onBubbleDestroyed(GameBubble gameBubble);
    }
}
