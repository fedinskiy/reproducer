package com.quarkus.qe;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(APPLICATION_JSON)
    @Path("/dto")
    public StringDTO hello() {
        return new StringDTO("first", "second");
    }
}
