package com.redhat.qe;

import io.smallrye.mutiny.Uni;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class Resource {

    @GET
    @Path("/validate/reactive/{content}")
    @Size(min = 3, max = 3, message = "response must have 3 characters")
    public Uni<String> validateReactiveEcho(@PathParam("content") String content) {
        return Uni.createFrom().item(content);
    }

    @GET
    @Path("/validate/classic/{content}")
    @Size(min = 3, max = 3, message = "response must have 3 characters")
    public String validateEcho(@PathParam("content") String content) {
        return content;
    }

    @GET
    @Path("/validate/input/{content}")
    public Uni<String> reactiveEcho(
            @Size(min = 3, max = 3, message = "request must have 3 characters") @PathParam("content") String content) {
        return Uni.createFrom().item(content);
    }
}
