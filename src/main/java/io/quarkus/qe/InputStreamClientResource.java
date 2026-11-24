package io.quarkus.qe;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Path("/client/input-stream")
public class InputStreamClientResource {

    public static final String CONTENT = "Hello from an InputStream";

    @Inject
    @RestClient
    InputStreamClient client;


    @GET
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public long count() {
        return client.count(contentStream());
    }

    @GET
    @Path("/uni-count")
    @Produces(MediaType.TEXT_PLAIN)
    public Uni<Long> uniCount() {
        return client.uniCount(contentStream());
    }

    @GET
    @Path("/headers")
    @Produces(MediaType.TEXT_PLAIN)
    public String headers() {
        return client.headers(contentStream());
    }

    private static ByteArrayInputStream contentStream() {
        return new ByteArrayInputStream(CONTENT.getBytes(StandardCharsets.UTF_8));
    }
}
