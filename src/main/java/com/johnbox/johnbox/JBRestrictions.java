package com.johnbox.johnbox;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class JBRestrictions {
  public String type = new String();
  public Optional<Double> min;
  public Optional<Double> max;
  public Optional<Double> increment;

  public JBRestrictions() {
  }

  public JBRestrictions(String type, Optional<Double> min, Optional<Double> max, Optional<Double> increment) {
    this.type = type;
    this.min = min;
    this.max = max;
    this.increment = increment;
  }
}
