package com.lumoza.bubbleshooter.service.physic;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

/**
 * Base empty contact listener.
 * Useful to subclass and override only those methods you are interested in.
 */
public abstract class BaseContactListener implements ContactListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void beginContact(Contact contact) {
        // pass
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void endContact(Contact contact) {
        // pass
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // pass
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // pass
    }
}
