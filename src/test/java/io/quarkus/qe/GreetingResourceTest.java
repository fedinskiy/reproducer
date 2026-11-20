package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;

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
    public void sendInvalidJSONPayload() {
        String jsonPayload =getInvalidJSONPayload();
        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when()
                .post("/football/upload-football-json");
        Assertions.assertEquals(500, response.statusCode(),
                "Body is " + response.body().asString());
        System.out.println(response.body().asString());
        response
                .then()
                .body(containsString("Internal error: Invalid token=CURLYOPEN"),
                        containsString("Expected tokens are: [COMMA]"));
    }

    private String getInvalidJSONPayload() {
        return """
[
  {
    "name": "Germany",
    "colorTShirt": "White",
    "euroCups": 3
  }
  {
    "name": "Spain",
    "colorTShirt": "Red",
    "euroCups": 3
  }
  invalid
  {
    "name": "France",
    "colorTShirt": "Blue",
    "euroCups":
  },
  {
    "name": "Denmark",
    "colorTShirt": "White",
    "euroCups": 1
  }
  ]
                """;
    }

}