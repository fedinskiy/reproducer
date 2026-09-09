package io.quarkus.qe;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface Bot {
    // Using Multi enables streaming.
    String chat(@UserMessage String question);
}
