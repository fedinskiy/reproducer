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
        System.out.println(response.headers().asList());
        assertEquals(MediaType.TEXT_HTML, response.contentType());
        String result = response.body().asString();
        for (char c : result.toCharArray()) {
            if (Character.isWhitespace(c)) {
                System.out.println((int) c);
            }
        }
        assertEquals(59, result.length());
        assertEquals("<html>\n    <p>This page is rendered by Quarkus</p>\n</html>\n", response.body().asString());
    }
}
