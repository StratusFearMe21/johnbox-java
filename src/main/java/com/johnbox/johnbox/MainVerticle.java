// Code adapted from vert.x starter
// https://start.vertx.io

package com.johnbox.johnbox;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.Json;
import io.vertx.core.json.jackson.DatabindCodec;

import java.util.HashMap;
import java.util.Optional;

import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class MainVerticle extends AbstractVerticle {
  HashMap<String, JBEntity> entities = new HashMap<String, JBEntity>();
  HashMap<Integer, JBProfile> here = new HashMap<Integer, JBProfile>();
  int room_serial = 1;

  @Override
  // Code adapted from vert.x web documentation
  // https://vertx.io/docs/vertx-web/java/
  public void start(Promise<Void> startPromise) throws Exception {
    HttpServer server = vertx.createHttpServer();

    io.vertx.core.json.jackson.DatabindCodec.mapper()
        .registerModule(new Jdk8Module());

    // Adapted from Understanding Vert.x: working with WebSockets
    // https://alexey-soshin.medium.com/playing-ping-pong-over-websockets-with-vert-x-447c634c6c87
    server.webSocketHandler((ctx) -> {
      WSQuery query = new WSQuery(ctx.query().split("=|&"));
      JBResult welcome = JBResult.CLIENT_WELCOME;
      JBProfileRoles roles;
      switch (query.role) {
        case "player":
          roles = JBProfileRoles.player;
          roles.name = query.name;
          break;
        default:
          roles = JBProfileRoles.host;
          break;
      }
      JBProfile client = new JBProfile(this.room_serial, query.user_id, query.role, query.name,
          roles, ctx);
      // TODO: reconnection
      welcome.result = new ClientWelcome(client.id, "s8f89sf8das8f8", false, this.entities.values(), this.here,
          client);

      client.send(welcome);

      this.here.put(client.id, client);
      this.room_serial += 1;

      if (client.role.equals("player")) {
        JBResult connected = JBResult.CLIENT_CONNECTED;
        connected.result = new ClientConnected(client.id, client.userId, client.name, client.role, false, client);

        this.here.get(1).send(connected);
      }
      ctx.textMessageHandler((text_msg) -> {
        WSMessage msg = Json.decodeValue(text_msg, WSMessage.class);
        switch (msg.params) {
          case TEXT_CREATE:
          case TEXT_SET:
          case TEXT_UPDATE:
          case OBJECT_CREATE:
          case OBJECT_SET:
          case OBJECT_UPDATE:
          case NUMBER_CREATE:
          case NUMBER_SET:
          case NUMBER_UPDATE:
            try {
              create_entity(msg.params.create_params, msg.params.scope(), client);
              client.ok(msg.seq);
            } catch (Exception e) {
              e.printStackTrace();
              client.err(e, msg.seq);
            }
            break;
          case TEXT_GET:
          case NUMBER_GET:
          case OBJECT_GET:
            JBEntity get_entity = this.entities.get(msg.params.key_param);
            JBResult obj_result;
            switch (msg.params.scope()) {
              case "text":
                obj_result = JBResult.TEXT;
                break;
              case "number":
                obj_result = JBResult.NUMBER;
                break;
              default:
                obj_result = JBResult.OBJECT;
                break;
            }
            obj_result.result = get_entity.object;
            client.send(obj_result, Optional.of(msg.seq));
            break;
          case LOCK:
            JBEntity entity = this.entities.get(msg.params.key_param);
            entity.attributes.locked = true;
            entity.object.from = client.id;
            JBResult lock_msg = JBResult.LOCK;
            lock_msg.result = DatabindCodec.mapper().createObjectNode().put("key", msg.params.key_param).put("from",
                client.id);

            this.distributeMessage(lock_msg, client);
            client.ok(msg.seq);
            break;
          case ROOM_EXIT:
            this.entities.clear();
            for (JBProfile h : this.here.values()) {
              h.socket.close();
            }
            this.here.clear();
            break;
          default:
            client.ok(msg.seq);
        }
      });
    }).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("Server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }

  private void create_entity(JBCreateParams params, String jb_type, JBProfile profile) throws Exception {
    Optional<JBEntity> prev_value = Optional.ofNullable(this.entities.get(params.key));
    boolean has_been_created = prev_value.isEmpty();
    boolean is_unlocked = prev_value.isPresent() && !prev_value.get().attributes.locked;
    // TODO
    boolean has_perms = true;
    if (!(has_been_created || is_unlocked || has_perms) && !profile.role.equals("host")) {
      throw new Exception("permission denied");
    }

    JBEntity entity = new JBEntity(jb_type,
        new JBObject(params.key, params.val, params.restrictions,
            prev_value.isPresent() ? prev_value.get().object.version + 1 : 0,
            profile.id),
        prev_value.isPresent() ? prev_value.get().attributes
            : new JBAttributes(false, prev_value.isPresent() ? prev_value.get().attributes.acl : params.acl));

    this.entities.put(params.key, entity);

    JBResult new_msg;
    switch (jb_type) {
      case "text":
        new_msg = JBResult.TEXT;
        break;
      case "object":
        new_msg = JBResult.OBJECT;
        break;
      case "number":
        new_msg = JBResult.NUMBER;
        break;
      // TODO: doodles
      default:
        throw new Exception("not a valid type: " + jb_type);
    }

    new_msg.result = entity.object;
    this.distributeMessage(new_msg, profile);
  }

  public void distributeMessage(JBResult msg, JBProfile profile) {
    for (JBProfile sending_profile : this.here.values()) {
      if (sending_profile.id != profile.id) {
        sending_profile.send(msg);
      }
    }
  }
}
