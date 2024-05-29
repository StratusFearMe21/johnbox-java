package com.johnbox.johnbox;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

public class JBObject {
  public String key;
  public Optional<JsonNode> val = Optional.empty();
  public JBRestrictions restrictions;
  public int version;
  public int from;

  public JBObject(String key, JBRestrictions restrictions, int version, int from) {
    this.key = key;
    this.restrictions = restrictions;
    this.version = version;
    this.from = from;
  }

  public JBObject(String key, Optional<JsonNode> val, JBRestrictions restrictions, int version, int from) {
    this.key = key;
    this.val = val;
    this.restrictions = restrictions;
    this.version = version;
    this.from = from;
  }
}
