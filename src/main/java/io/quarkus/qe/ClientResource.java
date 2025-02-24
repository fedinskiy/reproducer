package io.quarkus.qe;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/client/book")
public class ClientResource {

    @Inject
    @RestClient
    BookClient bookInterface;


    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Book> getResource(@QueryParam("title") String title, @QueryParam("author") String author) {
        System.out.println("Getting book " + title + " from " + author);
        Uni<Book> book = bookInterface.getBook(title, author);
        return book;
    }
}
