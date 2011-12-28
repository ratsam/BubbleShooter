package com.lumoza.bubbleshooter.service.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Collection of contact info.
 */
public class ContactInfoHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<ContactInfo> contacts;
    private boolean finished;

    /**
     * Default constructor.
     */
    public ContactInfoHistory() {
        contacts = new ArrayList<ContactInfo>();
        finished = false;
    }

    /**
     * Add contact info to history.
     * Contact info only added if there wasn't "final" contact info yet.
     *
     * @param contactInfo contact info to add
     */
    public synchronized void addContactInfo(ContactInfo contactInfo) {
        if (!finished) {
            contacts.add(contactInfo);

            if (contactInfo.isFinalContact()) {
                finished = true;
            }
        }
    }

    public List<ContactInfo> getContacts() {
        return contacts;
    }

    public boolean isFinished() {
        return finished;
    }

    /**
     * Reset history state so that it can be reused.
     * For "testbeds" generally.
     */
    public synchronized void reset() {
        contacts.clear();
        finished = false;
    }
}
