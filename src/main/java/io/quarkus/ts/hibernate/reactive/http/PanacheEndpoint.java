package io.quarkus.ts.hibernate.reactive.http;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.ts.hibernate.reactive.database.Author;
import io.quarkus.ts.hibernate.reactive.database.AuthorRepository;
import io.quarkus.ts.hibernate.reactive.database.Book;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/library")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PanacheEndpoint {

    @Inject
    AuthorRepository authors;

    @GET
    @Path("books")
    public Multi<String> all() {
        return Book.all()
                .map(Book::getTitle);
    }

    @GET
    @Path("books/{id}")
    public Uni<Response> find(Integer id) {
        return Book.byId(id)
                .map(book -> book == null
                        ? Response.status(Response.Status.NOT_FOUND)
                        : Response.ok(book.getTitle()))
                .map(Response.ResponseBuilder::build);
    }

    @GET
    @Path("book")
    public Uni<Response> getConstant() {
        return Uni.createFrom().item(Response.ok("Slovník").build());
    }

    @PUT
    @Path("books/{author}/{name}")
    public Uni<Response> createBook(Integer author, String name) {
        return Panache.withTransaction(() -> Book.create(author, name))
                .map(nothing -> Response.status(Response.Status.CREATED))
                .onFailure().recoverWithItem(error -> Response.status(Response.Status.BAD_REQUEST).entity(error.getMessage()))
                .map(Response.ResponseBuilder::build);
    }

    @GET
    @Path("books/author/{name}")
    public Uni<List<String>> search(String name) {
        return authors.findByName(name)
                .map(Collection::stream)
                .map(authorStream -> authorStream
                        .map(Author::getBooks)
                        .flatMap(Collection::stream)
                        .map(Book::getTitle)
                        .collect(Collectors.toList()));
    }

    @GET
    @Path("authors")
    public Uni<List<Author>> authors() {
        return authors.listAll();
    }

    @GET
    @Path("author/{id}")
    public Uni<Response> author(Integer id) {
        return authors.findById(id)
                .map(author -> author == null
                        ? Response.status(Response.Status.NOT_FOUND)
                        : Response.ok(author.getName()))
                .map(Response.ResponseBuilder::build);
    }

    @POST
    @Path("author/{name}")
    public Uni<Response> createAuthor(String name) {
        return authors.create(name)
                .map(ignored -> Response.status(Response.Status.CREATED))
                .onFailure().recoverWithItem(throwable -> {
                    return Response.status(Response.Status.BAD_REQUEST).entity(throwable.getMessage());
                })
                .map(Response.ResponseBuilder::build);
    }

    @DELETE
    @Path("author/{id}")
    public Uni<Response> deleteAuthor(Integer id) {
        return authors.deleteById(id)
                .call(isDeleted -> authors.flush())
                .map(isDeleted -> isDeleted
                        ? Response.status(Response.Status.NO_CONTENT)
                        : Response.status(Response.Status.NOT_FOUND))
                .map(Response.ResponseBuilder::build);
    }

    @GET
    @Path("by-author/{name}")
    public Uni<Response> searchByAuthor(String name) {
        return Book
                .find("SELECT book.title \nFROM Book book \n JOIN Author author on author.id=book.author\n WHERE author.name=?1",
                        name)
                .project(BookDescription.class).list()
                .map(books -> books.isEmpty()
                        ? Response.status(Response.Status.NOT_FOUND)
                        : Response.ok(books))
                .onFailure().recoverWithItem(error -> Response.status(Response.Status.BAD_REQUEST).entity(error.getMessage()))
                .map(Response.ResponseBuilder::build);
    }
}
