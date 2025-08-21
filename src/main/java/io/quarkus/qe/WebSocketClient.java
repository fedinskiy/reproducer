package io.quarkus.qe;

import io.quarkus.websockets.next.BasicWebSocketConnector;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/socket")
@ApplicationScoped
public class WebSocketClient {
    private static final Logger LOG = Logger.getLogger(WebSocketClient.class);

    private WebSocketClientConnection connection;

    @Inject
    BasicWebSocketConnector connector;

    private final List<String> answers = Collections.synchronizedList(new ArrayList<>());

    @Path("start")
    @POST
    public boolean openConnection(String uri) {
        connection = connector
                .baseUri(uri)
                .path("/chat")
                .executionModel(BasicWebSocketConnector.ExecutionModel.NON_BLOCKING)
                .onTextMessage((connection, message) -> {
                    LOG.info("Message: " + message);
                    answers.add(message);
                })
                .connectAndAwait();
        return true;
    }

    @Path("send")
    @POST
    public Uni<Void> sendMessage(String message) {
        return connection.sendText(message);
    }

    @GET
    @Path("answer")
    public String getAnswer() {
        return answers.get(answers.size() - 1);
    }
}
