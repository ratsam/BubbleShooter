package com.lumoza.bubbleshooter.client.html;

import com.lumoza.bubbleshooter.client.core.BubbleShooter;
import com.lumoza.bubbleshooter.client.core.input.InputEventManagerPlaynKeyboardImpl;
import playn.core.Game;
import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

/**
 * HTML implementation for Game object.
 */
public class BubbleShooterHtml extends HtmlGame {

    /**
     * Perform start of Game.
     */
    @Override
    public final void start() {
        HtmlPlatform platform = HtmlPlatform.register();
        platform.assetManager().setPathPrefix("client/");
        PlayN.run(createGame());
    }

    private Game createGame() {
        final InputEventManagerPlaynKeyboardImpl inputEventManager = new InputEventManagerPlaynKeyboardImpl();
        PlayN.keyboard().setListener(inputEventManager);

        final BubbleShooter game = new BubbleShooter();
        game.setInputEventManager(inputEventManager);
        return game;
    }
}
