package io.quarkus.qe;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/slow-topic")
public class SlowTopicResource {

    @Inject
    @Channel("slow-topic")
    Emitter<String> emitter;

    @POST
    @Path("/sendMessages/{content}")
    public void sendMessage(@PathParam("content") String content) {
            emitter.send("Message " + content);
    }
}
