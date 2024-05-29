package com.johnbox.johnbox;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

// Adapted from Baeldung
// https://www.baeldung.com/jackson-serialize-enums
public class JBProfileRolesSerializer extends StdSerializer<JBProfileRoles> {

    public JBProfileRolesSerializer() {
        super(JBProfileRoles.class);
    }

    @Override
    public void serialize(
            JBProfileRoles roles, JsonGenerator generator, SerializerProvider provider)
            throws IOException, JsonProcessingException {
        generator.writeStartObject();
        switch (roles) {
            case host:
                generator.writeFieldName("host");
                generator.writeStartObject();
                generator.writeEndObject();
                break;
            case player:
                generator.writeFieldName("player");
                generator.writeStartObject();
                generator.writeFieldName("name");
                generator.writeString(roles.name);
                generator.writeEndObject();
                break;
        }
        generator.writeEndObject();
    }
}