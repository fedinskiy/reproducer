package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
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
                .when().get("/book");
        assertEquals(200, response.statusCode());
        ResponseBody body = response.body();
        assertEquals("Joseph Heller", body.jsonPath().getString("author"), body.asString());
        assertEquals("Catch-22", body.jsonPath().getString("title"), body.asString());
    }

    @Test
    public void classTest() {
        given()
                .get("/class/io.quarkus.qe.Book")
                .then()
                .statusCode(200)
                .body(containsString("Book"));
        given()
                .get("/class/io.quarkus.qe.Shmook")
                .then()
                .statusCode(404);

        given()
                .get("/class/io.quarkus.qe.Book$quarkusjacksonserializer")
                .then()
                .statusCode(200)
                .body(containsString("Book$quarkusjacksonserializer"));
    }

}