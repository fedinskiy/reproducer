package io.quarkus.qe;

import dev.langchain4j.model.chat.listener.ChatModelListener;
import dev.langchain4j.model.chat.listener.ChatModelResponseContext;
import dev.langchain4j.model.openai.OpenAiChatModel;
import io.quarkiverse.langchain4j.ModelBuilderCustomizer;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.Map;

@ApplicationScoped
public class ModelCustomizer implements ModelBuilderCustomizer<OpenAiChatModel.OpenAiChatModelBuilder> {
    private static final Logger LOG = Logger.getLogger(ModelCustomizer.class);

    @Override
    public void customize(OpenAiChatModel.OpenAiChatModelBuilder builder) {
        LOG.info("Customizing OpenAiChatModel");
        builder
                .customHeaders(Map.of("X-QE-Header", "custom value"))
                .listeners(new ChatModelListener() {
                    @Override
                    public void onResponse(ChatModelResponseContext context) {
                        LOG.infof("Response from model %s", context.modelProvider().name());
                    }
                });
    }
}
