package de.byoc.inbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class InboxDienstImpl implements InboxDienst {

  private final Map<String, List<String>> contentMap = new ConcurrentHashMap<>();
  
  @Inject
  private Event<InboxAktualisiert> events;

  @Override
  public List<String> list(String ref) {
    return Collections.unmodifiableList(contentMap.getOrDefault(ref, Collections.emptyList()));
  }

  @Override
  public void speichere(String ref, String location) {
    contentMap.computeIfAbsent(ref, r -> new ArrayList<>()).add(location);
    events.fire(new InboxAktualisiert(ref, list(ref)));
  }

}
