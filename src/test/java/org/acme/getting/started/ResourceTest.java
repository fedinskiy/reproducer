package org.acme.getting.started;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ResourceTest {

    @Test
    public void testGreetingEndpoint() {
        Response creation=given().put("library/book/2/Around_the_World_in_Eighty_Days");
        assertEquals(HttpStatus.SC_CREATED, creation.statusCode());
        assertEquals("4", creation.getBody().asString());

        Response response = given()
                .when().get("library/book/4");
        assertEquals(HttpStatus.SC_OK, response.statusCode());
        assertEquals("Around_the_World_in_Eighty_Days", response.getBody().asString());
    }

}
