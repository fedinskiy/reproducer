package com.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        Response response = given()
                .get("/hello/dto");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("{\"partOne\":\"first\",\"partTwo\":\"second\"}",
                response.body().asString());

//        response
//                .then()
//                .statusCode(200)
//                .body("partOne", Matchers.is("first"))
//                .body("partTwo", Matchers.is("second"));
    }

}