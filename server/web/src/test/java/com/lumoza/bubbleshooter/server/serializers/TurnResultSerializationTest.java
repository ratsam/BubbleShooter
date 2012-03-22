package com.lumoza.bubbleshooter.server.serializers;

import com.lumoza.bubbleshooter.server.TurnResult;
import com.lumoza.bubbleshooter.service.game.ContactInfo;
import com.lumoza.bubbleshooter.service.game.GameBubble;
import com.lumoza.bubbleshooter.service.game.Position;
import junit.framework.Assert;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;

public class TurnResultSerializationTest {

    private final static String EXPECTED_STRING = "{\"success\":true,\"contacts\":[{\"posX\":5.0,\"posY\":10.0,\"type\":\"BOUNDS\",\"finalContact\":true}],\"bubbles\":[{\"color\":10,\"position\":{\"row\":1,\"column\":6}}]}";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void doTest() throws IOException {
        final TurnResult result = createTurnResult();

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JsonGenerator generator = this.objectMapper.getJsonFactory().createJsonGenerator(outputStream);
        this.objectMapper.writeValue(generator, result);

        Assert.assertEquals(EXPECTED_STRING, outputStream.toString());
    }

    private TurnResult createTurnResult() {
        final TurnResult result = new TurnResult();
        result.setSuccess(true);
        result.setBubbles(createTurnResultBubbles());
        result.setContacts(createTurnResultContacts());
        return result;
    }

    private Iterable<GameBubble> createTurnResultBubbles() {
        return Collections.singleton(createGameBubble());
    }

    private GameBubble createGameBubble() {
        final GameBubble gameBubble = new GameBubble();
        gameBubble.setColor(10);
        gameBubble.setPosition(new Position(1, 6));
        return gameBubble;
    }

    private Iterable<ContactInfo> createTurnResultContacts() {
        return Collections.singleton(createContactInfo());
    }

    private ContactInfo createContactInfo() {
        final ContactInfo contactInfo = new ContactInfo();
        contactInfo.setFinalContact(true);
        contactInfo.setType(ContactInfo.ContactType.BOUNDS);
        contactInfo.setPosX(5F);
        contactInfo.setPosY(10F);
        return contactInfo;
    }
}
