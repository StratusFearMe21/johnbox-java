package com.johnbox.johnbox;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JBAttributes {
  public boolean locked;
  @JsonIgnore
  public ArrayList<String> acl;

  public JBAttributes(boolean locked, ArrayList<String> acl) {
    this.locked = locked;
  }
}
