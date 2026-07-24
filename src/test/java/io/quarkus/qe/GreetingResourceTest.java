package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().header("Content-Type", "illegal").get("hello")
          .then()
             .statusCode(415);
    }

    @Test
    public void testSubEndpoint() {
        given()
                .when().when().header("Content-Type", "illegal").get("hello/sub/")
                .then()
                .statusCode(415);
    }

    @Test
    public void testAnotherSubEndpoint() {
        given()
                .when().when().header("Content-Type", "illegal").get("hello/sub2/")
                .then()
                .statusCode(415);
    }

}