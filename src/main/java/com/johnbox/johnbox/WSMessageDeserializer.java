package com.johnbox.johnbox;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

// Adapted from Baeldung
// https://www.baeldung.com/jackson-serialize-enums
public class WSMessageDeserializer extends StdDeserializer<WSMessage> {
    public WSMessageDeserializer() {
        this(WSMessage.class);
    }

    public WSMessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public WSMessage deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.readValueAsTree();

        String opcode = node.get("opcode").asText();

        JBCreateParams create_params = new JBCreateParams();
        String key_param = new String();

        switch (opcode) {
            case "text/create":
            case "text/set":
            case "text/update":
            case "number/create":
            case "number/set":
            case "number/update":
            case "object/create":
            case "object/set":
            case "object/update":
                create_params = node.get("params").traverse(io.vertx.core.json.jackson.DatabindCodec.mapper())
                        .readValueAs(JBCreateParams.class);
                break;
            case "text/get":
            case "number/get":
            case "object/get":
            case "lock":
            case "drop":
                key_param = node.get("params").get("key").asText();
        }

        JBParams params;

        switch (opcode) {
            case "text/create":
                params = JBParams.TEXT_CREATE;
                params.create_params = create_params;
                break;
            case "text/set":
                params = JBParams.TEXT_GET;
                params.create_params = create_params;
                break;
            case "text/update":
                params = JBParams.TEXT_UPDATE;
                params.create_params = create_params;
                break;
            case "number/create":
                params = JBParams.NUMBER_CREATE;
                params.create_params = create_params;
                break;
            case "number/set":
                params = JBParams.NUMBER_SET;
                params.create_params = create_params;
                break;
            case "number/update":
                params = JBParams.NUMBER_UPDATE;
                params.create_params = create_params;
                break;
            case "object/create":
                params = JBParams.OBJECT_CREATE;
                params.create_params = create_params;
                break;
            case "object/set":
                params = JBParams.OBJECT_SET;
                params.create_params = create_params;
                break;
            case "object/update":
                params = JBParams.OBJECT_UPDATE;
                params.create_params = create_params;
                break;
            case "text/get":
                params = JBParams.TEXT_GET;
                params.key_param = key_param;
                break;
            case "number/get":
                params = JBParams.NUMBER_GET;
                params.key_param = key_param;
                break;
            case "object/get":
                params = JBParams.OBJECT_GET;
                params.key_param = key_param;
                break;
            case "lock":
                params = JBParams.LOCK;
                params.key_param = key_param;
                break;
            case "drop":
                params = JBParams.DROP;
                params.key_param = key_param;
                break;
            case "room/exit":
                params = JBParams.ROOM_EXIT;
                break;
            default:
                params = JBParams.OTHER;
        }

        int seq = node.get("seq").asInt();

        return new WSMessage(params, seq);
    }
}
