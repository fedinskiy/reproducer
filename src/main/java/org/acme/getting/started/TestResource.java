package org.acme.getting.started;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/library")
public class TestResource {

    @PUT
    @Path("book/{author}/{name}")
    public Uni<Response> createBook(Integer author, String name) {
        return Panache.withTransaction(() -> Book.create(author, name))
                .map(book -> Response.status(Response.Status.CREATED).entity(book.getId()))
                .onFailure().recoverWithItem(error -> Response.status(Response.Status.BAD_REQUEST).entity(error.getMessage()))
                .map(Response.ResponseBuilder::build);
    }

    @GET
    @Path("book/{id}")
    public Uni<Response> find(Integer id) {
        return Book.byId(id)
                .map(book -> book == null
                        ? Response.status(Response.Status.NOT_FOUND)
                        : Response.ok(book.getTitle()))
                .map(Response.ResponseBuilder::build);
    }
}