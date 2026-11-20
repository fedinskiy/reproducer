package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        for (int i = 0; i < 3; i++) {
            given()
                    .when().get("/hello")
                    .then()
                    .statusCode(200)
                    .body(is("Hello from RESTEasy Reactive"));
        }
        Awaitility.await()
                .atMost(5, TimeUnit.SECONDS)
                .pollInterval(200, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> assertEquals(3, Files.lines(Path.of("target/appAccess.log")).count(),
                        "There should be 3 lines in access log."));

    }

}