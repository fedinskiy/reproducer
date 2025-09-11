package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.stringContainsInOrder;

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
    public void testHttp10WithoutHostHeader() throws InterruptedException, IOException {
        // We can't use java client as it's always adding `\r\n` to the host header.
        StringBuilder response = new StringBuilder();
        String host = RestAssured.baseURI.split("//")[1];
        int port = RestAssured.port;
        System.out.println("Accessing " + host + " on port " + port);
        try (Socket s = new Socket(host, port);
             PrintWriter pw = new PrintWriter(s.getOutputStream());
             BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));) {
            pw.print("GET /hello HTTP/1.0\n\r\n");
            pw.flush();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line).append("/n");
            }
        }

        assertThat(response.toString(), stringContainsInOrder("HTTP/1.0", "200 OK"));
    }
}