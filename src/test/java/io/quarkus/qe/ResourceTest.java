package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ResourceTest {
    @Test
    public void testJsonPropertyEndpoint() {
        Response response = given()
                .when().get("/json/properties");
        assertEquals(200, response.statusCode());
        ResponseBody body = response.body();
        assertEquals("The book is called Fall and is written by Neal Stephenson", body.asString());
    }

    @Test
    public void testRecordPropertyEndpoint() {
        Response response = given()
                .when().get("/record/properties");
        assertEquals(200, response.statusCode());
        ResponseBody body = response.body();
        assertEquals("The book is called Fall and is written by Neal Stephenson", body.asString());
    }

    @Test
    public void testJsonArrayEndpoint() {
        Response response = given()
                .when().get("/json/array");
        assertEquals(200, response.statusCode());
        ResponseBody body = response.body();
        assertEquals("The book has 2 characters", body.asString());
    }


    @Test
    public void testRecordArrayEndpoint() {
        Response response = given()
                .when().get("/record/array");
        assertEquals(200, response.statusCode());
        ResponseBody body = response.body();
        assertEquals("The book has 2 characters", body.asString());
    }

    @Test
    public void testJsonLoopEndpoint() {
        Response response = given()
                .when().get("/json/loop");
        assertEquals(200, response.statusCode());
        String[] content = response.body().asString().split("\n");
        assertEquals("1. Corvallis as well as", content[0]);
        assertEquals("2. Zula", content[1]);
    }

    @Test
    public void testRecordLoopEndpoint() {
        Response response = given()
                .when().get("/record/loop");
        assertEquals(200, response.statusCode());
        String[] content = response.body().asString().split("\n");
        assertEquals("1. Corvallis as well as", content[0]);
        assertEquals("2. Zula", content[1]);
    }

}