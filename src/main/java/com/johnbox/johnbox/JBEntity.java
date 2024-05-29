package com.johnbox.johnbox;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class JBEntity {
  public JBType type;
  public JBObject object;
  public JBAttributes attributes;

  public JBEntity(JBType type, JBObject object, JBAttributes attributes) {
    this.type = type;
    this.object = object;
    this.attributes = attributes;
  }

  public JBEntity(String t, JBObject object, JBAttributes attributes) {
    switch (t) {
      case "text":
        this.type = JBType.text;
      case "number":
        this.type = JBType.number;
      case "object":
        this.type = JBType.object;
      default:
        this.type = JBType.object;
    }

    this.object = object;
    this.attributes = attributes;
  }
}
