package com.johnbox.johnbox;

public class ClientConnected {
    public int id;
    public String userId;
    public String name;
    public String role;
    public boolean reconnect;
    public JBProfile profile;

    public ClientConnected(int id, String userId, String name, String role, boolean reconnect, JBProfile profile) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.reconnect = reconnect;
        this.profile = profile;
    }
}
