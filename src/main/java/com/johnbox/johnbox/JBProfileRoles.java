package com.johnbox.johnbox;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = JBProfileRolesSerializer.class)
public enum JBProfileRoles {
    host(null),
    player(null);

    public String name;

    private JBProfileRoles(String name) {
        this.name = name;
    }
}