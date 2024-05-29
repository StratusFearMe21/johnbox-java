package com.johnbox.johnbox;

import java.util.Optional;

public class WSQuery {
  public String user_id;
  public String format;
  public String name;
  public String role;
  public Optional<String> host_token = Optional.empty();
  public Optional<String> secret = Optional.empty();
  public int id = 0;

  public WSQuery(String[] query) {
    for (int i = 0; i < query.length; i += 2) {
      String key = query[i];
      String value = query[i + 1];

      switch (key) {
        case "user-id":
          this.user_id = value;
          break;
        case "format":
          this.format = value;
          break;
        case "name":
          this.name = value;
          break;
        case "role":
          this.role = value;
          break;
        case "host_token":
          this.host_token = Optional.of(value);
          break;
        case "secret":
          this.secret = Optional.of(value);
          break;
        case "id":
          this.id = Integer.parseInt(value);
          break;
      }
    }
  }
}
