package io.quarkus.qe;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/client")
public class ClientResource {

    @RestClient
    Client client;

    @GET
    @Path("basic")
    public String getBasic(){
        return "Client says: " + client.root();
    }
}
