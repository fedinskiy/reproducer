import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.RequestOptions;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(VertxExtension.class)
public class ReproducerTest {
    @Test
    void ping(Vertx vertx, VertxTestContext context) {
        final HttpClient client = vertx.createHttpClient();
        final RequestOptions options = new RequestOptions()
                .setPort(443)
                .setHost("vertx.io")
                .setURI("/docs/")
                .setSsl(true);
        client.get(options).handler(response-> {
            context.verify(()-> {
                    Assertions.assertEquals(200, response.statusCode());
                    context.completeNow();
            });
        })
        .end();
    }
}
