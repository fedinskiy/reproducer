package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
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
    public void userA() {
        given()
                .auth().basic("alice", "rabbit")
                .get("/authorized")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo("Hello, user alice"));
    }

    @Test
    public void userB() {
        given()
                .auth().basic("bob", "builder")
                .get("/authorized")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);

    }

    @Test
    public void userI() {
        given()
                .auth().basic("isaac", "N3wt0N")
                .get("/authorized")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body(equalTo("Hello, user isaac"));
    }

}