
package de.byoc.inbox;

import java.util.List;

public interface InboxDienst {
  
  List<String> list(String ref);
  void speichere(String ref, String location);

}
