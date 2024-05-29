package com.johnbox.johnbox;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.json.Json;
import io.vertx.core.json.jackson.DatabindCodec;

public class JBProfile {
    public int id;
    @JsonIgnore
    public int pc = 0;
    public String userId;
    public String role;
    public String name;
    public JBProfileRoles roles;
    @JsonIgnore
    public ServerWebSocket socket;

    public JBProfile(int id, String userId, String role, String name, JBProfileRoles roles, ServerWebSocket socket) {
        this.id = id;
        this.userId = userId;
        this.role = role;
        this.name = name;
        this.roles = roles;
        this.socket = socket;
    }

    public void send(JBResult msg, Optional<Integer> re) {
        this.pc += 1;
        this.socket.writeTextMessage(Json.encode(new JBMessage(this.pc, re, msg)));
    }

    public void send(JBResult msg) {
        send(msg, Optional.empty());
    }

    public void err(Exception e, int re) {
        JBResult err = JBResult.ERROR;
        err.result = e;
        this.send(err, Optional.of(re));
    }

    public void ok(int re) {
        JBResult ok = JBResult.OK;
        ok.result = DatabindCodec.mapper().createObjectNode();
        this.send(ok, Optional.of(re));
    }
}
