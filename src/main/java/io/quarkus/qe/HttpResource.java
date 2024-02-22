package io.quarkus.qe;

import io.quarkus.grpc.GrpcClient;
import io.quarkus.ts.grpc.Greeter;
import io.quarkus.ts.grpc.HelloRequest;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/api")
public class HttpResource {

    @GrpcClient
    Greeter plain;

    @GET
    @Path("/grpc/{name}")
    public Uni<String> grpc(String name) {
        return plain.sayHello(HelloRequest.newBuilder().setName(name).build())
                .onItem().transform(helloReply -> helloReply.getMessage());
    }

    @GET
    @Path("http/{name}")
    public Uni<String> http(String name) {
        return Uni.createFrom().item("Http greets you, " + name);
    }
}
