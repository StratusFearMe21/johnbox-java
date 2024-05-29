package com.johnbox.johnbox;

import java.util.ArrayList;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.JsonNode;

public class JBCreateParams {
    public ArrayList<String> acl;
    public String key;
    public Optional<JsonNode> val = Optional.empty();
    @JsonUnwrapped
    public JBRestrictions restrictions;

    public JBCreateParams() {
    }

    public JBCreateParams(ArrayList<String> acl, String key, Optional<JsonNode> val, JBRestrictions restrictions) {
        this.acl = acl;
        this.key = key;
        this.val = val;
        this.restrictions = restrictions;
    }
}
