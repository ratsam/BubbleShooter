package com.lumoza.bubbleshooter.server;

import com.lumoza.bubbleshooter.service.GamePhysicProcessor;
import com.lumoza.bubbleshooter.service.game.ContactInfo;
import com.lumoza.bubbleshooter.service.game.GameBubble;
import com.lumoza.bubbleshooter.service.game.GameWorld;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

/**
 * Temporary controller. Stores game in memory (so there is only one game at the moment).
 */
@Controller
public class GameController {

    private GamePhysicProcessor physicProcessor;
    private GameWorld gameWorld;

    /**
     * Return game info.
     *
     * @return game info.
     */
    @RequestMapping("/game/")
    @ResponseBody
    public GameStateInfo getGameInfo() {

        final Iterable<GameBubble> bubbles = gameWorld.getBubbles();

        final GameStateInfo info = new GameStateInfo();
        info.setBubbles(bubbles);
        return info;
    }

    /**
     * Perform game turn.
     *
     * @param turnCommand command to perform turn
     * @return turn result
     */
    @RequestMapping(value = "/game/turn.do"/*, method = RequestMethod.POST*/)
    @ResponseBody
    public TurnResult doTurn(@Valid TurnCommand turnCommand) {
        physicProcessor.fire(turnCommand.getAngle());
        physicProcessor.runPhysics();

        final Iterable<ContactInfo> contacts = physicProcessor.processContactsHistory();

        final TurnResult result = new TurnResult();
        result.setSuccess(true);
        result.setContacts(contacts);
        result.setBubbles(gameWorld.getBubbles());
        return result;
    }

    /**
     * Redirect to correct game info page.
     *
     * @return redirect view
     */
    @RequestMapping("/game")
    public View redirectToGameView() {
        return new RedirectView("/game/");
    }

    @Autowired
    public void setPhysicProcessor(GamePhysicProcessor physicProcessor) {
        this.physicProcessor = physicProcessor;
    }

    @Autowired
    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
}
