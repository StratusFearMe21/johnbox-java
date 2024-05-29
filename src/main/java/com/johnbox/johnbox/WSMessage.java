package com.johnbox.johnbox;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = WSMessageDeserializer.class)
public class WSMessage {
    public JBParams params;
    public int seq;

    public WSMessage(JBParams params, int seq) {
        this.params = params;
        this.seq = seq;
    }

    public WSMessage() {
    }

}
