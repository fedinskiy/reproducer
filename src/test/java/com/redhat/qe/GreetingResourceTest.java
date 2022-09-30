package com.redhat.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @Test
    public void fetchDefault() {
        Response response = given().get("/validate/boom");
        System.out.println(response.headers());
        System.out.println(response.asPrettyString());
        Assertions.assertEquals(400, response.statusCode());
        response.then().contentType(MediaType.TEXT_PLAIN);
    }

    @Test
    public void fetchText() {
        Response response = given().get("/validate/text/boom");
        System.out.println(response.headers());
        System.out.println(response.asPrettyString());
        Assertions.assertEquals(400, response.statusCode());
        response.then().contentType(MediaType.TEXT_PLAIN);
    }
}
