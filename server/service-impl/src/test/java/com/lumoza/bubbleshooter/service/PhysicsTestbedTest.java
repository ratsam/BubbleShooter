package com.lumoza.bubbleshooter.service;

import com.lumoza.bubbleshooter.service.game.GameBubble;
import com.lumoza.bubbleshooter.service.game.GameMap;
import com.lumoza.bubbleshooter.service.game.GameWorld;
import com.lumoza.bubbleshooter.service.game.GameWorldMapBasedImpl;
import com.lumoza.bubbleshooter.service.game.HexagonalGameMap;
import com.lumoza.bubbleshooter.service.game.Position;
import org.jbox2d.common.Vec2;
import org.jbox2d.testbed.framework.TestbedSettings;
import org.jbox2d.testbed.framework.TestbedTest;

public class PhysicsTestbedTest extends TestbedTest {

    private static final int ROWS_COUNT = 12;
    private static final int ROW_SIZE_MAX = 8;

    private GamePhysicProcessor gamePhysicProcessor;

    @Override
    public void initTest(boolean argDeserialized) {
        setTitle("Testing Bubble Shooter physics");

        gamePhysicProcessor = new GamePhysicProcessor(ROWS_COUNT, ROW_SIZE_MAX);

        getWorld().setGravity(new Vec2(0, 0)); // Set zero gravity
        getWorld().setContinuousPhysics(true);

        GameWorld gameWorld = new GameWorldMapBasedImpl(createGameWorldMap());
        gamePhysicProcessor.setGameWorld(gameWorld);
        gamePhysicProcessor.setPhysicWorld(getWorld());

        gamePhysicProcessor.init();
    }

    private GameMap createGameWorldMap() {
        final GameMap gameMap = new HexagonalGameMap(ROWS_COUNT, ROW_SIZE_MAX);
        fillMapBubbles(gameMap);
        return gameMap;
    }

    private void fillMapBubbles(GameMap map) {
        final int preFillRows = (int) (Math.random() * ROWS_COUNT);
        for (int row = 0; row < preFillRows; row++) {
            int columns = ROW_SIZE_MAX - row % 2;
            for (int column = 0; column < columns; column++) {
                final GameBubble gameBubble = new GameBubble();
                gameBubble.setPosition(new Position(row, column));
                gameBubble.setColor(1);

                map.addBubble(gameBubble);
            }
        }
    }

    @Override
    protected void _reset() {
        gamePhysicProcessor.reset();
        super._reset();
    }

    @Override
    public void mouseMove(Vec2 p) {
        super.mouseMove(p);
    }

    @Override
    public void keyPressed(char argKeyChar, int argKeyCode) {
        if (argKeyChar == 'f') { // Fire button pressed
            gamePhysicProcessor.fire();
            return;
        }

        super.keyPressed(argKeyChar, argKeyCode);
    }

    @Override
    public String getTestName() {
        return "Bubble Shooter physics";
    }

    @Override
    public synchronized void step(TestbedSettings settings) {
        gamePhysicProcessor.processContactsHistory();

        super.step(settings);
    }
}
