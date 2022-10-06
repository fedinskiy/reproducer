package com.redhat.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void reactive() {
        Response response = given().get("/validate/reactive/mouse");
        response.then().body(containsString("response must have 3 characters"));
        Assertions.assertEquals(400, response.statusCode());
    }

    @Test
    public void classic() {
        Response response = given().get("/validate/classic/mouse");
        response.then().body(containsString("response must have 3 characters"));
        Assertions.assertEquals(400, response.statusCode());
    }

    @Test
    public void input() {
        Response response = given().get("/validate/input/mouse");
        response.then().body(containsString("request must have 3 characters"));
        Assertions.assertEquals(400, response.statusCode());
    }

    @Test
    public void classicSuccess() {
        Response response = given().get("/validate/classic/cat");
        Assertions.assertEquals("cat", response.body().asString());
        Assertions.assertEquals(200, response.statusCode());
    }
}
