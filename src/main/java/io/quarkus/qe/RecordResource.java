package io.quarkus.qe;

import io.quarkus.qute.Qute;
import io.vertx.core.json.JsonObject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/record")
public class RecordResource {

    private static final Book BOOK = new Book("Fall", "Neal Stephenson", "Corvallis", "Zula");

    @GET
    @Path("/properties")
    @Produces(MediaType.TEXT_PLAIN)
    public String getProperties() {
        return Qute.fmt("The book is called {book.title} and is written by {book.['author']}")
                .data("book", BOOK).render();
    }

    @GET
    @Path("/array")
    @Produces(MediaType.TEXT_PLAIN)
    public String getArray() {
        return Qute.fmt("The book has {book.characters.length} characters")
                .data("book", BOOK).render();
    }

    @GET
    @Path("/loop")
    @Produces(MediaType.TEXT_PLAIN)
    public String getLoop() {
        String template = """
                {#for character in book.characters}
                {character_count}. {character}{#if character_hasNext} {#if character_odd }as well as{/if}{#if character_even }and also{/if}{/if}
                {/for}
                """;
        return Qute.fmt(template)
                .data("book", BOOK).render();
    }
}
