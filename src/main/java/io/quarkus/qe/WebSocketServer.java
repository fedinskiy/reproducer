package io.quarkus.qe;

import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.smallrye.mutiny.Uni;

@WebSocket(path = "/chat")
public class WebSocketServer {
    @OnTextMessage
    public Uni<String> answer(String input) {
        return Uni.createFrom().item("And " + input + " to you too!");
    }

}