package com.johnbox.johnbox;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class JBMessage {
  public int pc;
  public Optional<Integer> re = Optional.empty();
  @JsonUnwrapped
  public JBResult result;

  public JBMessage(int pc, JBResult result) {
    this.pc = pc;
    this.result = result;
  }

  public JBMessage(int pc, Optional<Integer> re, JBResult result) {
    this.pc = pc;
    this.re = re;
    this.result = result;
  }
}
