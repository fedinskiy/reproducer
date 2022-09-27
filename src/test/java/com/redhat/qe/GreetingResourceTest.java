package com.redhat.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void failOnClassic() {
        Response response = given().get("/validate/boom");
        System.out.println(response.asPrettyString());
        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("numeric value out of bounds", response.body().jsonPath().getString("violations[0].message"));
    }

    @Test
    public void failOnReactive() {
        Response response = given().get("/validate/boom");
        System.out.println(response.asPrettyString());
        Assertions.assertEquals(400, response.statusCode());
        Assertions.assertEquals("numeric value out of bounds", response.body().jsonPath().getString("parameterViolations[0].message"));
    }
}
