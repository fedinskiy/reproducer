package com.redhat.qe;

import io.smallrye.mutiny.Uni;

import javax.validation.constraints.Digits;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public class Resource {

    @GET
    @Path("/validate/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<String> validateNoProduces(
            @Digits(integer = 5, fraction = 0, message = "numeric value out of bounds") @PathParam("id") String id) {
        return Uni.createFrom().item(id);
    }
}
