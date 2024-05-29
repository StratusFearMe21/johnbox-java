package com.johnbox.johnbox;

import java.util.Collection;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

// import java.util.Map;

public class ClientWelcome {
  public int id;
  public String secret;
  public boolean reconnect;
  public String deviceId = "0000000000.0000000000000000000000";
  @JsonFormat(shape = JsonFormat.Shape.ARRAY)
  public Collection<JBEntity> entities;
  public Map<Integer, JBProfile> here;
  public JBProfile profile;

  public ClientWelcome(int id, String secret, boolean reconnect, Collection<JBEntity> entities,
      Map<Integer, JBProfile> here, JBProfile profile) {
    this.id = id;
    this.secret = secret;
    this.reconnect = reconnect;
    this.entities = entities;
    this.here = here;
    this.profile = profile;
  }
}
