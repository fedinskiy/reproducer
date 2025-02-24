package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void testBookEndpoint() {
        Response response = given()
                .when().get("/book?title=Catch-22&author=Heller");
        assertEquals(200, response.statusCode());
        ResponseBody body = response.body();
        assertEquals("Joseph Heller", body.jsonPath().getString("author"), body.asString());
        assertEquals("Catch-22", body.jsonPath().getString("title"), body.asString());
    }

    @Test
    public void testClientEndpoint() {
        Response response = given()
                .when()
                .get("client/book?title=Catch-22&author=Heller");
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        String json = response.body().asString();
        System.out.println("json: " + json + " " + json.length());
        assertEquals("Catch-22", response.jsonPath().getString("title"));
        assertEquals("Heller", response.jsonPath().getString("author"));
    }
}