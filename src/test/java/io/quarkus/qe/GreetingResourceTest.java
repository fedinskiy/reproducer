package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GreetingResourceTest {

    @Test
    @Order(1)
    public void smoke() {
        given()
                .get("/socket/start/chatbot")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        Response response = given().get("/socket/answers/1");
        Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
        Assertions.assertEquals("Hello, I'm Bob, how can I help you?", response.body().asString());
    }

    @Test
    public void easyRag() {
        given()
                .body("What is the opening deposit for a standard savings account?")
                .post("/socket/send")
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
        Awaitility.await().atMost(30, TimeUnit.SECONDS).untilAsserted(() -> {
            Response response = given().get("/socket/answers/2");
            Assertions.assertEquals(HttpStatus.SC_OK, response.statusCode());
            Assertions.assertTrue(response.body().asString().contains("$25"));
        });
    }
}