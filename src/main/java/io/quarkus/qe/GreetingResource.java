package io.quarkus.qe;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
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
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from RESTEasy Reactive";
    }

    @GET
    @Path("/book")
    @Produces(MediaType.APPLICATION_JSON)
    public Book book() {
        return new Book("Catch-22", "Joseph Heller");
    }

    @GET
    @Path("/class/{name}")
    public Response getClass(@PathParam("name") String className) {
        try {
            Class<?> existingclass = Class.forName(className);
            return Response.ok(existingclass.getCanonicalName()).build();
        } catch (ClassNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND.getStatusCode(),
                    "There is no such class: " + className).build();
        }
    }
}
