package de.byoc.inbox;

import java.util.List;

public class InboxAktualisiert {

  private final String ref;
  private final List<String> locations;

  public InboxAktualisiert(String ref, List<String> locations) {
    this.ref = ref;
    this.locations = locations;
  }

  public String ref() {
    return ref;
  }

  public List<String> locations() {
    return locations;
  }
  
}
