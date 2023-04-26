package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
public class GreetingResourceTest {
    @Test
    public void getAll() {
        given()
                .get("/book")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .body("", hasSize(7));
    }
}
