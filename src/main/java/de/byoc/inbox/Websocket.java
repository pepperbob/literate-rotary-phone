package de.byoc.inbox;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ApplicationScoped
@ServerEndpoint("/news")
public class Websocket {

  private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
  
  @Inject
  private InboxDienst dienst;
  
  @OnOpen
  public void onOpen(Session session) throws IOException {
    if(session.getQueryString() == null) {
      session.close(new CloseReason(() -> 0, "No Channel."));
      return;
    }
    
    sessions.add(session);
  }
  
  @OnClose
  public void onClose(Session session) {
    sessions.remove(session);
  }
  
  @OnMessage
  public void onMessage(String message, Session session) {
    session.getAsyncRemote().sendText(
            toJson(new InboxAktualisiert(
                    session.getQueryString(), 
                    dienst.list(session.getQueryString()))));
  }
  
  public void falls(@Observes InboxAktualisiert event) {
    sessions.stream().filter(s -> event.ref().equals(s.getQueryString()))
             .forEach(s -> s.getAsyncRemote().sendText(toJson(event)));
  }
  
  private String toJson(InboxAktualisiert e) {
      JsonArrayBuilder locations = Json.createArrayBuilder();
      e.locations().forEach(locations::add);
      return Json.createObjectBuilder()
              .add("ref", e.ref())
              .add("locations", locations.build())
              .build()
              .toString();
  }

}
