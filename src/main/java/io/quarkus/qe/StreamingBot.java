package io.quarkus.qe;

import dev.langchain4j.service.UserMessage;
import io.smallrye.mutiny.Multi;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public interface StreamingBot {
    // Using Multi enables streaming.
    Multi<String> chat(@UserMessage String question);
}
