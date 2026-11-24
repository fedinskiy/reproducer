package io.quarkus.qe;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.io.InputStream;

@RegisterRestClient(configKey = "input-stream")
@Path("/input-stream")
public interface InputStreamClient {

    @POST
    @Path("/count")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.TEXT_PLAIN)
    long count(InputStream data);

    @POST
    @Path("/count-reactive")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.TEXT_PLAIN)
    Uni<Long> uniCount(InputStream data);

    @POST
    @Path("/headers")
    @Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @Produces(MediaType.TEXT_PLAIN)
    String headers(InputStream data);
}
