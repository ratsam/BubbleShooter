package com.lumoza.bubbleshooter.service;

import com.lumoza.bubbleshooter.service.game.ContactInfo;
import com.lumoza.bubbleshooter.service.game.ContactInfoHistory;
import com.lumoza.bubbleshooter.service.game.GameBubble;
import com.lumoza.bubbleshooter.service.game.GameWorld;
import com.lumoza.bubbleshooter.service.game.Position;
import com.lumoza.bubbleshooter.service.physic.BaseContactListener;
import com.lumoza.bubbleshooter.service.physic.Edges;
import com.lumoza.bubbleshooter.service.physic.PhysicObjectsConstructor;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

/**
 * Glue between Game and Physic objects.
 */
public class GamePhysicProcessor {

    private static final float TRIANGLE_HEIGHT_MOD = (float) (Math.sqrt(3) / 2);

    private static final float BUBBLE_SIZE = 2.0f;

    private final int rowsCount;
    private final int rowSizeMax;

    private final PhysicObjectsConstructor physicObjectsConstructor;

    private GameWorld gameWorld;
    private World physicWorld;

    private Body fireBubble;
    private Edges edges;

    private ContactInfoHistory contactInfoHistory = new ContactInfoHistory();

    /**
     * Constructor.
     *
     * @param rowsCount rows count to use
     * @param rowSizeMax max row size to use
     */
    public GamePhysicProcessor(int rowsCount, int rowSizeMax) {
        this.rowsCount = rowsCount;
        this.rowSizeMax = rowSizeMax;

        physicObjectsConstructor = new PhysicObjectsConstructor(rowsCount, rowSizeMax, BUBBLE_SIZE);
    }

    /**
     * Initialize method.
     * To be used after all properties was set.
     */
    public void init() {
        edges = physicObjectsConstructor.createEdges();
        reset();
    }

    /**
     * Reset.
     * To be used from "testbeds" generally.
     */
    public void reset() {
        createPhysicBubbles();
    }

    private void createPhysicBubbles() {
        for (GameBubble bubble : gameWorld.getBubbles()) {
            physicObjectsConstructor.createBubble(getBubbleCoordinates(bubble.getPosition()));
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
        return ((rowsCount - row) * TRIANGLE_HEIGHT_MOD - 0.5f) * BUBBLE_SIZE;
    }

    private float getBubbleXCoordinate(int row, int column) {
        float posX = (rowSizeMax - (rowSizeMax - column) + 0.5f) * BUBBLE_SIZE;
        if (row % 2 != 0) {
            posX += BUBBLE_SIZE / 2;
        }

        return posX;
    }

    /**
     * Create fire bubble (if not exists yet).
     */
    public void fire() {
        if (fireBubble == null) {
            fireBubble = physicObjectsConstructor.createFireBubble();

            // TODO: calc vector depending on mouse position
            fireBubble.setLinearVelocity(new Vec2((int) (Math.random() * 18) - 9, (int) (Math.random() * 35)));
        }
    }

    /**
     * Perform contacts history processing.
     */
    public void processContactsHistory() {
        for (ContactInfo contactInfo : contactInfoHistory.getContacts()) {
            processContactInfo(contactInfo);
        }
        contactInfoHistory.reset();
    }

    private void processContactInfo(ContactInfo contactInfo) {
        if (contactInfo.isFinalContact()) {
            if (fireBubble != null) {
                physicWorld.destroyBody(fireBubble);
                fireBubble = null;

                final Position gamePosition = getNearestEmptyGamePosition(contactInfo.getPosX(), contactInfo.getPosY());
                gameWorld.processFireBubble(gamePosition);
            }
        }
    }

    private Position getNearestEmptyGamePosition(float posX, float posY) {
        final NearestEmptyPositionResolver resolver = new NearestEmptyPositionResolver(BUBBLE_SIZE, gameWorld);
        return resolver.resolveForCoordinates(posX, posY);
    }

    private void preSolveContact(Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        if (isFireBubble(fixtureA)) {
            processFireBubbleContact(fixtureB);
        } else if (isFireBubble(fixtureB)) {
            processFireBubbleContact(fixtureA);
        }
    }

    private void processFireBubbleContact(Fixture other) { // TODO: extract ContactInfoEmitter
        if (!contactInfoHistory.isFinished()) {
            doProcessContact(other);
        }
    }

    private void doProcessContact(Fixture other) {
        final ContactInfo contactInfo = createContactInfo(fireBubble.getPosition());

        if (edges.isTopEdge(other)) {
            processTopEdgeContact(contactInfo);
        } else if (edges.isLeftEdge(other)) {
            processLeftEdgeContact(contactInfo);
        } else if (edges.isRightEdge(other)) {
            processRightEdgeContact(contactInfo);
        } else {
            processBubbleContact(contactInfo);
        }

        contactInfoHistory.addContactInfo(contactInfo);
    }

    private ContactInfo createContactInfo(Vec2 position) {
        final ContactInfo contactInfo = new ContactInfo();
        contactInfo.setPosX(position.x);
        contactInfo.setPosY(position.y);
        return contactInfo;
    }

    private void processTopEdgeContact(ContactInfo contactInfo) {
        contactInfo.setFinalContact(true);
        contactInfo.setType(ContactInfo.ContactType.BOUNDS);
    }

    private void processLeftEdgeContact(ContactInfo contactInfo) {
        contactInfo.setType(ContactInfo.ContactType.BOUNDS);
    }

    private void processRightEdgeContact(ContactInfo contactInfo) {
        contactInfo.setType(ContactInfo.ContactType.BOUNDS);
    }

    private void processBubbleContact(ContactInfo contactInfo) {
        contactInfo.setFinalContact(true);
        contactInfo.setType(ContactInfo.ContactType.BUBBLE);
    }

    private boolean isFireBubble(Fixture fixture) {
        return fixture.getBody().equals(fireBubble);
    }

    /**
     * Set game world and attach listeners to game events.
     *
     * @param gameWorld game world to use
     */
    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        attachGameWorldListeners();
    }

    private void attachGameWorldListeners() {
        gameWorld.addBubbleCreationListener(new GameWorld.BubbleCreationListener() {
            public void onBubbleCreated(GameBubble gameBubble) {
                physicObjectsConstructor.createBubble(getBubbleCoordinates(gameBubble.getPosition()));
            }
        });

        gameWorld.addBubbleDestroyingListener(new GameWorld.BubbleDestroyingListener() {
            public void onBubbleDestroyed(GameBubble gameBubble) {
                System.out.println("Bubble destroyed");
            }
        });
    }

    /**
     * Set physic world and attach listeners to physic events.
     *
     * @param physicWorld physic world to use
     */
    public void setPhysicWorld(World physicWorld) {
        this.physicWorld = physicWorld;

        physicObjectsConstructor.setPhysicWorld(physicWorld);

        physicWorld.setContactListener(new BaseContactListener() {
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                GamePhysicProcessor.this.preSolveContact(contact);
            }
        });
    }
}
