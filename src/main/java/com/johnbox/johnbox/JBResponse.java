package com.johnbox.johnbox;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

public class JBResponse {
  public boolean ok;
  @JsonUnwrapped
  public JBResponseBody body;

  public JBResponse() {
  }

  public JBResponse(boolean ok, JBResponseBody body) {
    this.ok = ok;
    this.body = body;
  }

  public String toString() {
    return this.ok + "\n" + this.body.body + "\n" + this.body.error;
  }
}
