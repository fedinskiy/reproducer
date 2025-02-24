package io.quarkus.qe;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    public Uni<Book> book() {
        return Uni.createFrom().item(new Book("Catch-22", "Joseph Heller"));
    }

    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Book> getBook(@QueryParam("title") String title, @QueryParam("author") String author) {
        System.out.println("Creating book " + title + " from " + author);
        return Uni.createFrom().item(new Book(title, author));
    }

    @POST
    @Path("/sequel")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<String> sequel(Book first) {
        return Uni.createFrom().item(first.getTitle() + " II");
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
