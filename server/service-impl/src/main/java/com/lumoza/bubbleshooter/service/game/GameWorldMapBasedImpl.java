package com.lumoza.bubbleshooter.service.game;

import java.util.ArrayList;
import java.util.List;

/**
 * GameWorld implementation based on GameMap.
 */
public class GameWorldMapBasedImpl implements GameWorld {

    private final GameMap gameMap;

    private final List<BubbleCreationListener> bubbleCreationListeners;
    private final List<BubbleDestroyingListener> bubbleDestroyingListeners;

    /**
     * Constructor.
     *
     * @param gameMap game map to use
     */
    public GameWorldMapBasedImpl(GameMap gameMap) {
        this.gameMap = gameMap;

        bubbleCreationListeners = new ArrayList<BubbleCreationListener>();
        bubbleDestroyingListeners = new ArrayList<BubbleDestroyingListener>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<GameBubble> getBubbles() {
        return gameMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBubbleCreationListener(BubbleCreationListener listener) {
        bubbleCreationListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBubbleDestroyingListener(BubbleDestroyingListener listener) {
        bubbleDestroyingListeners.add(listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLandingPosition(Position position) {
        return isLandingPosition(position.getRow(), position.getColumn());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLandingPosition(int row, int column) {
        if (gameMap.findBubble(row, column) != null) {
            return false;
        }
        return row == 0 || !gameMap.findNeighbors(row, column).isEmpty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int rowsCount() {
        return gameMap.rowsCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasBubbles(int row) {
        return gameMap.hasBubbles(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int rowSize(int row) {
        return gameMap.rowSize(row);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processFireBubble(Position position) {
        // TODO: we should either add bubble or "break" bubbles group.

        final GameBubble bubble = new GameBubble();
        bubble.setPosition(position);
        bubble.setColor(1); // TODO: get last fire color, set it, retrieve next fire color
        gameMap.addBubble(bubble);
        broadcastBubbleAdded(bubble);
    }

    private void broadcastBubbleAdded(GameBubble bubble) {
        for (BubbleCreationListener listener : bubbleCreationListeners) {
            listener.onBubbleCreated(bubble);
        }
    }
}
