package com.lumoza.bubbleshooter.server;

import com.lumoza.bubbleshooter.service.game.ContactInfo;
import com.lumoza.bubbleshooter.service.game.GameBubble;
import org.codehaus.jackson.map.annotate.JsonSerialize;
//import org.codehaus.jackson.map.ser.BeanSerializer;

import java.io.Serializable;

/**
 * Represents result of performed game turn.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL, typing = JsonSerialize.Typing.STATIC)
public class TurnResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;
    private Iterable<ContactInfo> contacts;
    private Iterable<GameBubble> bubbles;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

//    @JsonSerialize(using = ContainerSerializers.IterableSerializer.class)
    public Iterable<ContactInfo> getContacts() {
        return contacts;
    }

    public void setContacts(Iterable<ContactInfo> contacts) {
        this.contacts = contacts;
    }

    //@JsonSerialize(using = BeanSerializer.class)
    public Iterable<GameBubble> getBubbles() {
        return bubbles;
    }

    public void setBubbles(Iterable<GameBubble> bubbles) {
        this.bubbles = bubbles;
    }
}
