import generated.GreeterGrpc;
import generated.HelloRequest;
import io.grpc.ManagedChannel;
import io.vertx.core.Vertx;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.OpenSSLEngineOptions;
import io.vertx.grpc.VertxChannelBuilder;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Objects;

@ExtendWith(VertxExtension.class)
public class ReproducerTest {
	@BeforeEach
	void setUp(Vertx vertx) {
		vertx.deployVerticle(new Server());
	}

	@Test
	public void reproduce(Vertx vertx, VertxTestContext context) {
		String keyStorePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("client-truststore.jks")).getFile();
		ManagedChannel channel = VertxChannelBuilder
						.forAddress(vertx, "localhost", 8443)
						.useSsl(options -> options.setSsl(true)
										.setUseAlpn(true)
										.setSslEngineOptions(new OpenSSLEngineOptions())
										.setTrustStoreOptions(new JksOptions()
														.setPath(keyStorePath)
														.setPassword("wibble")))
						.build();
		GreeterGrpc.GreeterVertxStub stub = GreeterGrpc.newVertxStub(channel);
		HelloRequest request = HelloRequest.newBuilder().setName("world").build();
		stub.sayHello(request, asyncResponse -> {
			if (asyncResponse.succeeded()) {
				context.verify(() -> {
					Assertions.assertEquals("world", asyncResponse.result().getMessage());
					context.completeNow();
				});
			} else {
				context.failNow(asyncResponse.cause());
			}
		});
	}
}
