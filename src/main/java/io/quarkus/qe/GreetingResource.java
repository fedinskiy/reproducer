package io.quarkus.qe;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String root() {
        return "Yes, it works";
    }

    @GET
    @Path("/hello")
    public Response hello() {
        return Response.ok("hello").header("Transfer-Encoding", "chunked").build();
    }
}
