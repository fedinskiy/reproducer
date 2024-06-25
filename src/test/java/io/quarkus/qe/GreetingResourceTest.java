package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void fastTest() throws InterruptedException {
        given()
          .when().post("/slow-topic/sendMessages/fast")
          .then()
                .statusCode(204);
    }

    @Test
    public void slowTest() throws InterruptedException {
        Thread.sleep(10_000);
        given()
                .when().post("/slow-topic/sendMessages/wait")
                .then()
                .statusCode(204);
    }
}