package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    public void webSocket() {
        given()
                .when().body("ws://localhost:8081").post("/socket/start")
                .then()
                .statusCode(200);
        given()
                .when().body("hello").post("/socket/send")
                .then()
                .statusCode(204);
        given()
                .when().get("/socket/answer")
                .then()
                .statusCode(200)
                .body(is("And hello to you too!"));
    }

    @Test
    public void emptyPrefix() {
        given()
                .when().body("localhost:8081").post("/socket/start")
                .then()
                .statusCode(200);
        given()
                .when().body("hello").post("/socket/send")
                .then()
                .statusCode(204);
        given()
                .when().get("/socket/answer")
                .then()
                .statusCode(200)
                .body(is("And hello to you too!"));
    }

    @Test
    public void http() {
        given()
                .when().body("http://localhost:8081").post("/socket/start")
                .then()
                .statusCode(200);
        given()
                .when().body("hello").post("/socket/send")
                .then()
                .statusCode(204);
        given()
                .when().get("/socket/answer")
                .then()
                .statusCode(200)
                .body(is("And hello to you too!"));
    }

}