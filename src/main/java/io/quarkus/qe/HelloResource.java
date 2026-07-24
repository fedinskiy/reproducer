package io.quarkus.qe;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("/hello")
public class HelloResource {
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    public Uni<String> get() {
        return Uni.createFrom().item("c");
    }

    @Path("sub")
    @Consumes(MediaType.TEXT_PLAIN)
    public SubResource sub() {
        return new SubResource();
    }

    @GET
    @Path("sub2")
    @Consumes(MediaType.TEXT_PLAIN)
    public SubResource sub2() {
        return new SubResource();
    }

    private static class SubResource {
        @GET
        @Consumes(MediaType.TEXT_PLAIN)
        public String get() {
            return "sub-answer";
        }
    }
}
