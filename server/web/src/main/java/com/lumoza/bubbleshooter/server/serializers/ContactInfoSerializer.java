package com.lumoza.bubbleshooter.server.serializers;

import com.lumoza.bubbleshooter.service.game.ContactInfo;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

/**
 * JSON serializer that handles {@link ContactInfo} serialization.
 */
public class ContactInfoSerializer extends JsonSerializer<ContactInfo> {

    @Override
    public Class<ContactInfo> handledType() {
        return ContactInfo.class;
    }

    @Override
    public void serialize(ContactInfo value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();
        jgen.writeNumberField("posX", value.getPosX());
        jgen.writeNumberField("posY", value.getPosY());
        jgen.writeBooleanField("final", value.isFinalContact());
        jgen.writeEndObject();
    }
}
