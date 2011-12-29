package com.lumoza.bubbleshooter.service.physic;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

/**
 * Helper class for physic objects manipulation.
 */
public class PhysicObjectsConstructor {

    // TODO: get rid of this constant, use Position to Coordinates converter.
    private static final float TRIANGLE_HEIGHT_MOD = (float) (Math.sqrt(3) / 2);

    private final int rowsCount;
    private final int rowSizeMax;
    private final float bubbleSize;

    private World physicWorld;

    private BodyDef cachedFireBubbleBodyDef;
    private FixtureDef cachedFireBubbleFixtureDef;

    /**
     * Constructor.
     *
     * @param rowsCount game max rows count
     * @param rowSizeMax game max row size
     * @param bubbleSize bubble physic size
     */
    public PhysicObjectsConstructor(int rowsCount, int rowSizeMax, float bubbleSize) {
        this.rowsCount = rowsCount;
        this.rowSizeMax = rowSizeMax;
        this.bubbleSize = bubbleSize;
    }

    /**
     * Create Edges.
     *
     * @return new Edges instance
     */
    public Edges createEdges() {
        final float topY = rowsCount * bubbleSize * TRIANGLE_HEIGHT_MOD;
        final float bottomY = 0;
        final float leftX = 0;
        final float rightX = bubbleSize * rowSizeMax;

        final BodyDef bd = createBodyDef(new Vec2(0f, 0f), BodyType.STATIC);
        final Edges edges = new Edges(physicWorld.createBody(bd));

        edges.addTop(new Vec2(leftX, topY), new Vec2(rightX, topY));
        edges.addLeft(new Vec2(leftX, topY), new Vec2(leftX, bottomY));
        edges.addRight(new Vec2(rightX, topY), new Vec2(rightX, bottomY));

        return edges;
    }

    /**
     * Instantiates fire bubble.
     *
     * @return new fire bubble body
     */
    public Body createFireBubble() {
        Body body = physicWorld.createBody(createFireBubbleBodyDef());
        body.createFixture(createBubbleFixtureDef());

        return body;
    }

    private BodyDef createFireBubbleBodyDef() {
        if (cachedFireBubbleBodyDef == null) {
            cachedFireBubbleBodyDef = doCreateFireBubbleBodyDef();
        }
        return cachedFireBubbleBodyDef;
    }

    private BodyDef doCreateFireBubbleBodyDef() {
        final BodyDef bd = new BodyDef();
        bd.type = BodyType.DYNAMIC;
        bd.position = new Vec2(bubbleSize * rowSizeMax / 2f, 0);
        bd.bullet = true;
        bd.userData = "FireBubble";
        return bd;
    }

    private FixtureDef createBubbleFixtureDef() {
        if (cachedFireBubbleFixtureDef == null) {
            cachedFireBubbleFixtureDef = doCreateBubbleFixtureDef();
        }
        return cachedFireBubbleFixtureDef;
    }

    private FixtureDef doCreateBubbleFixtureDef() {
        final FixtureDef fd = new FixtureDef();
        final CircleShape cd = new CircleShape();
        cd.m_radius = bubbleSize / 2;
        fd.shape = cd;
        fd.restitution = 1; // Perfectly elastic collision
        fd.friction = 0;
        return fd;
    }

    /**
     * Create regular bubble body.
     *
     * @param position physic position for new body instance
     * @return new bubble body
     */
    public Body createBubble(Vec2 position) {
        return createBubble(position, null);
    }

    /**
     * Create regular bubble body with custom user data.
     *
     * @param position physic position for new body instance
     * @param userData user data to set for newly created Body instance
     * @return new bubble body
     */
    public Body createBubble(Vec2 position, Object userData) {
        final BodyDef bd = createBodyDef(position, BodyType.STATIC);
        final Body body = physicWorld.createBody(bd);
        body.setUserData(userData);
        body.createFixture(createBubbleFixtureDef());

        return body;
    }

    private BodyDef createBodyDef(Vec2 position, BodyType bodyType) {
        final BodyDef bd = new BodyDef();
        bd.fixedRotation = true;
        bd.position.set(position);
        bd.type = bodyType;

        return bd;
    }

    public void setPhysicWorld(World physicWorld) {
        this.physicWorld = physicWorld;
    }
}
