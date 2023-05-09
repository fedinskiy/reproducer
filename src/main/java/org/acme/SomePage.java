package org.acme;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import static java.util.Objects.requireNonNull;

@Path("/some-page")
public class SomePage {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String get(@QueryParam("name") String name) {
        return "Hello, "+name;
    }

}
