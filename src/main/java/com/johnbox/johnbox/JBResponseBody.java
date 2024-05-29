package com.johnbox.johnbox;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JBResponseBody {
  public Optional<String> body = Optional.empty();
  public Optional<String> error = Optional.empty();

  public JBResponseBody() {
  }

  public JBResponseBody(Optional<String> body, Optional<String> error) {
    this.body = body;
    this.error = error;
  }
}
