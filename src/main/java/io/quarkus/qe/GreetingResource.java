package io.quarkus.qe;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Path("/")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String root() {
        return "Yes, it works";
    }

    @GET
    @Path("/text")
    @io.quarkus.vertx.http.Uncompressed
    public Response text() {
        String content = createText();
        return Response.ok(content).build();
    }

    @GET
    @Path("/stream")
    public Response stream() {
        String content = createText();
        var stream=new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
        return Response.ok(stream).build();
    }

    private static String createText() {
        String content = IntStream.range(1, 100)
                .mapToObj(i -> "Hello no." + i)
                .collect(Collectors.joining("\n"));
        return content;
    }
}
