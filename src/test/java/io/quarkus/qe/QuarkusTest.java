package io.quarkus.qe;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@io.quarkus.test.junit.QuarkusTest
public class QuarkusTest {

    @Test
    public void testHelloEndpoint() {
        Response response = given().when().get("/book/1");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(new Book(1L, "Foundation", "Isaac Asimov"), response.body().as(Book.class));
    }

    @Test
    public void testNamed() {
        DockerImageName imageName = DockerImageName.parse("docker.io/library/mariadb:10.6").withTag("latest");
        GenericContainer container = new GenericContainer<>(imageName);
        container.start();
    }
}
