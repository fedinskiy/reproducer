import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class QuteTest {
    @Test
    void smoke() {
        Response response = given().get("/base");
        assertEquals(200, response.statusCode());
        assertTrue(response.body().asString().contains("<p>This page is rendered by Quarkus</p>"),
                response.body().asString());
        System.out.println(response.headers().asList());
        assertEquals(MediaType.TEXT_HTML, response.contentType());
    }

    @Test
    void location() {
        Response response = given().get("/located");
        assertEquals(200, response.statusCode());
        assertTrue(response.body().asString().contains("<p>This page is rendered by Quarkus</p>"),
                response.body().asString());
        System.out.println(response.headers().asList());
        assertEquals(MediaType.TEXT_HTML, response.contentType());
    }
}
