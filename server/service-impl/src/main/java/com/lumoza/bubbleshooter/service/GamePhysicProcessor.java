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

    private GameWorld gameWorld;
    private World physicWorld;

    private PositionToCoordinatesConverter positionConverter;
    private PhysicObjectsConstructor physicObjectsConstructor;
    private NearestEmptyPositionResolver landingPositionResolver;

    private Body fireBubble;
    private Edges edges;

    private ContactInfoHistory contactInfoHistory = new ContactInfoHistory();

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
            Vec2 coordinates = positionConverter.convert(bubble.getPosition());
            physicObjectsConstructor.createBubble(coordinates);
        }
    }

    /**
     * Create fire bubble (if not exists yet).
     *
     * @param degreesAngle angle (in degrees) to fire
     */
    public void fire(double degreesAngle) {
        if (fireBubble == null) {
            fireBubble = physicObjectsConstructor.createFireBubble();

            fireBubble.setLinearVelocity(calculateFireLinearVelocity(degreesAngle));
        }
    }

    private Vec2 calculateFireLinearVelocity(double degreesAngle) {
        final Vec2 velocityVector = new Vec2(50, 0);
        final double radiansAngle = Math.toRadians(degreesAngle);
        final double newX = velocityVector.x * Math.cos(radiansAngle) - velocityVector.y * Math.sin(radiansAngle);
        final double newY = velocityVector.x * Math.sin(radiansAngle) + velocityVector.y * Math.cos(radiansAngle);

        return new Vec2((float) newX, (float) newY);
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
        return landingPositionResolver.resolveForCoordinates(posX, posY);
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
            public void onBubbleCreated(GameBubble bubble) {
                Vec2 coordinates = positionConverter.convert(bubble.getPosition());
                physicObjectsConstructor.createBubble(coordinates);
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

        physicWorld.setContactListener(new BaseContactListener() {
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                GamePhysicProcessor.this.preSolveContact(contact);
            }
        });
    }

    public void setPositionConverter(PositionToCoordinatesConverter positionConverter) {
        this.positionConverter = positionConverter;
    }

    public void setPhysicObjectsConstructor(PhysicObjectsConstructor physicObjectsConstructor) {
        this.physicObjectsConstructor = physicObjectsConstructor;
    }

    public void setLandingPositionResolver(NearestEmptyPositionResolver landingPositionResolver) {
        this.landingPositionResolver = landingPositionResolver;
    }
}
