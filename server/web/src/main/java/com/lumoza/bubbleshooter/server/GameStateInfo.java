package com.lumoza.bubbleshooter.server;

import com.lumoza.bubbleshooter.service.game.GameBubble;

import java.io.Serializable;

/**
 * Represents current game state.
 */
public class GameStateInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Iterable<GameBubble> bubbles;

    public Iterable<GameBubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(Iterable<GameBubble> bubbles) {
        this.bubbles = bubbles;
    }
}
