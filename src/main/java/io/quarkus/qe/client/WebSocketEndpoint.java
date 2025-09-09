package io.quarkus.qe.client;

import io.quarkus.websockets.next.BasicWebSocketConnector;
import io.quarkus.websockets.next.WebSocketClientConnection;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.ConfigProvider;
import org.jboss.logging.Logger;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/socket")
@ApplicationScoped
public class WebSocketEndpoint {
    private static final Logger LOG = Logger.getLogger(WebSocketEndpoint.class);

    private WebSocketClientConnection connection;

    @Inject
    BasicWebSocketConnector connector;

    private final List<String> answers = Collections.synchronizedList(new ArrayList<>());

    @Path("start/{socket}")
    @GET
    public void openConnection(String socket) throws URISyntaxException {
        String host = ConfigProvider.getConfig().getValue("quarkus.http.host", String.class);
        Integer port = ConfigProvider.getConfig().getValue("quarkus.http.port", Integer.class);
        URI uri = new URI("ws", null, host, port, null, null, null);
        LOG.info("Connecting to: " + socket + "at " + uri);
        connection = connector
                .baseUri(uri)
                .path(socket)
                .executionModel(BasicWebSocketConnector.ExecutionModel.NON_BLOCKING)
                .onTextMessage((connection, message) -> {
                    LOG.info("Message: " + message);
                    answers.add(message);
                })
                .connectAndAwait();
    }

    @Path("send")
    @POST
    public Uni<Void> sendMessage(String message) {
        return connection.sendText(message);
    }

    @GET
    @Path("answers/{number}")
    public Response getAnswers(int number) {
        if (number > answers.size()) {
            return Response.noContent().build();
        } else {
            return Response.ok(answers.get(number - 1)).build();
        }
    }
}
