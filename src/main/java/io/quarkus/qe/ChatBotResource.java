package io.quarkus.qe;

import io.smallrye.mutiny.Multi;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/chat")
public class ChatBotResource {

    private final Bot bot;
    private final StreamingBot sbot;

    public ChatBotResource(Bot bot, StreamingBot sbot) {
        this.bot = bot;
        this.sbot = sbot;
    }

    @POST
    @Path("plain")
    public String get(@DefaultValue("What can you do?") String message) {
        return bot.chat(message);
    }

    @POST
    @Path("stream")
    public Multi<String> getStreaming(@DefaultValue("What can you do?") String message) {
        return sbot.chat(message);
    }
}
