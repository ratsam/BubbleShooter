package com.lumoza.bubbleshooter.service.physic;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

/**
 * Game box edges.
 */
public class Edges {

    private Body body;
    private Fixture topEdge;
    private Fixture leftEdge;
    private Fixture rightEdge;

    /**
     * Constructor.
     *
     * @param body physic body to use
     */
    public Edges(Body body) {
        this.body = body;
    }

    /**
     * Add top edge.
     *
     * @param v1 from
     * @param v2 to
     */
    public void addTop(Vec2 v1, Vec2 v2) {
        topEdge = create(v1, v2);
    }

    /**
     * Add left edge.
     *
     * @param v1 from
     * @param v2 to
     */
    public void addLeft(Vec2 v1, Vec2 v2) {
        leftEdge = create(v1, v2);
    }

    /**
     * Add right edge.
     *
     * @param v1 from
     * @param v2 to
     */
    public void addRight(Vec2 v1, Vec2 v2) {
        rightEdge = create(v1, v2);
    }

    /**
     * Check if given fixture is game box top edge.
     *
     * @param testFixture fixture to test
     * @return true if given fixture is game box top edge
     */
    public boolean isTopEdge(Fixture testFixture) {
        return topEdge.equals(testFixture);
    }

    /**
     * Check if given fixture is game box left edge.
     *
     * @param testFixture fixture to test
     * @return true if given fixture is game box left edge
     */
    public boolean isLeftEdge(Fixture testFixture) {
        return leftEdge.equals(testFixture);
    }

    /**
     * Check if given fixture is game box right edge.
     *
     * @param testFixture fixture to test
     * @return true if given fixture is game box right edge
     */
    public boolean isRightEdge(Fixture testFixture) {
        return rightEdge.equals(testFixture);
    }

    private Fixture create(Vec2 v1, Vec2 v2) {
        final PolygonShape ps = new PolygonShape();
        ps.setAsEdge(v1, v2);

        final Fixture fixture = body.createFixture(ps, 0);
        fixture.setRestitution(1);
        fixture.setFriction(0);

        return fixture;
    }
}
