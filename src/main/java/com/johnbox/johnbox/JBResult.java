package com.johnbox.johnbox;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum JBResult {
  CLIENT_WELCOME("client/welcome", null),
  CLIENT_CONNECTED("client/connected", null),
  TEXT("text", null),
  NUMBER("number", null),
  OBJECT("object", null),
  DOODLE("doodle", null),
  DOODLE_LINE("doodle/line", null),
  CLIENT_SEND("client/send", null),
  LOCK("lock", null),
  ERROR("error", null),
  OK("ok", null);

  public String opcode;
  public Object result;

  private JBResult(String opcode, Object result) {
    this.opcode = opcode;
    this.result = result;
  }
}
