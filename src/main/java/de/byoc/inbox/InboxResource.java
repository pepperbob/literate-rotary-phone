
package de.byoc.inbox;

import java.io.InputStream;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

@Path("/")
public class InboxResource {

  @Inject
  private InboxDienst dienst;
  
  @POST
  @Path("/{ref}/{filename}")
  public Response postToInbox(
          @PathParam("ref") String ref,
          @PathParam("filename") String filename,
          InputStream stream,
          @Context HttpHeaders headers) {
    dienst.speichere(ref, filename);
    return Response.accepted().build();
  }
  
}
