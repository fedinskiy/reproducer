import generated.GreeterGrpc;
import generated.HelloReply;
import generated.HelloRequest;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.net.JksOptions;
import io.vertx.core.net.OpenSSLEngineOptions;
import io.vertx.grpc.VertxServer;
import io.vertx.grpc.VertxServerBuilder;

import java.util.Objects;

public class Server extends AbstractVerticle {

	private VertxServer server;

	@Override
	public void start(Promise<Void> startPromise) {
		String keyStorePath = Objects.requireNonNull(this.getClass().getClassLoader().getResource("server-keystore.jks")).getFile();
		server = VertxServerBuilder.forPort(vertx, 8443)
						.addService(new GreeterGrpc.GreeterVertxImplBase() {
							@Override
							public void sayHello(HelloRequest request, Promise<HelloReply> future) {
								future.complete(HelloReply.newBuilder().setMessage(request.getName()).build());
							}
						})
						.useSsl(options -> options
										.setSsl(true)
										.setSslEngineOptions(new OpenSSLEngineOptions())
										.setUseAlpn(true)
										.setKeyStoreOptions(new JksOptions()
														.setPath(keyStorePath)
														.setPassword("wibble")))
						.build();
		server.start(asyncResult -> {
			if (asyncResult.succeeded()) {
				startPromise.complete();
			} else {
				startPromise.fail(asyncResult.cause());
			}
		});
	}

	@Override
	public void stop() throws Exception {
		server.shutdown(asyncResult -> {
			if (asyncResult.failed()) {
				throw new RuntimeException(asyncResult.cause());
			}
		});
		super.stop();
	}
}
