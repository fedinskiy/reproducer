package io.quarkus.qe;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/")
public class GreetingResource {

    @ConfigProperty(name = "quarkus.http-name")
    String name;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String root() {
        return "Yes, it works";
    }

    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello " + name;
    }
}
