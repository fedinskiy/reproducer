package io.quarkus.qe;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;

@Path("/")
public class GreetingResource {
    private static final Logger LOG = Logger.getLogger(GreetingResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String root() {
        LOG.info("Root access");
        return "Yes, it works";
    }

    @GET
    @Path("/long")
    @Produces(MediaType.TEXT_PLAIN)
    public String longMessage() {
        LOG.info("Message, which is very long and is not expected to fit into 64 bytes");
        return "Hello from RESTEasy Reactive";
    }
}
