package io.quarkus.ts.mcp.app;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api")
public class MCPServerRestAPI {

    @Inject
    FileServer server;

    @POST
    @Path("/create-tool")
    @Produces(MediaType.TEXT_PLAIN)
    public Response readResourceFile() {
        server.createTool();
        return Response.ok().build();
    }
}
