package io.quarkus.qe.debug;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class HelloIT {

    @Test
    public void testPing() {
        RestAssured
                .get("/ping")
                .then()
                .statusCode(200)
                .body(Matchers.is("ping"));
    }

    @Test
    public void testPong() {
        RestAssured
                .get("/pong")
                .then()
                .statusCode(200)
                .body(Matchers.is("pong"));
    }

}
