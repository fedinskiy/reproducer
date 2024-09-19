package io.quarkus.qe;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@QuarkusTest
public class GreetingResourceTest {

    private final static String DEFAULT_TEXT_PLAIN = Brotli4JResource.DEFAULT_TEXT_PLAIN;

    final static int CONTENT_LENGTH_DEFAULT_TEXT_PLAIN = DEFAULT_TEXT_PLAIN.length();

    private final static String JSON_CONTENT = "Hello from a JSON sample";

    private final static String BROTLI_ENCODING = "br";

    @Test
    public void checkTextPlainWithtBrotli4J() {
        int textPlainDataLenght = calculateTextLength("/sample.txt");
        assertBrotli4JCompression("/compression/text", MediaType.TEXT_PLAIN, BROTLI_ENCODING, BROTLI_ENCODING,
                textPlainDataLenght);
    }


    @Test
    public void checkXmlBrotli4JCompression() {
        int originalXMLLength = calculateXmlLength();
        assertBrotli4JCompression("/compression/brotli/xml", MediaType.APPLICATION_XML, BROTLI_ENCODING, BROTLI_ENCODING,
                originalXMLLength);
    }

    @Test
    public void checkJsonBrotli4JCompression() throws IOException {
        int originalJsonLength = calculateOriginalJsonLength();
        assertBrotli4JCompression("/compression/brotli/json", MediaType.APPLICATION_JSON, BROTLI_ENCODING, BROTLI_ENCODING,
                originalJsonLength);
    }

    @Test
    public void checkCompressedAndDecompressedWithQuarkus() {
        Response response = given()
                .header(HttpHeaders.ACCEPT_ENCODING, BROTLI_ENCODING)
                .get("/compression/default/text")
                .then()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_ENCODING, BROTLI_ENCODING)
                .extract().response();
        byte[] compressedBytes = response.asByteArray();
        Response decompressionResponse = given()
                .header(HttpHeaders.CONTENT_ENCODING, BROTLI_ENCODING)
                .body(compressedBytes)
                .post("/compression/decompression")
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(decompressionResponse.asString(), containsString(DEFAULT_TEXT_PLAIN));
    }

    @Test
    @Disabled
    public void checkCompressedAndDecompressedJSONWithQuarkus() {
        testCompressedAndDecompressed("/compression/brotli/json", JSON_CONTENT);
    }

    @Test
    @Disabled
    public void checkCompressedAndDecompressedXMLWithQuarkus() {
        testCompressedAndDecompressed("/compression/brotli/xml", "Bob Dylan");
    }

    public void assertBrotli4JCompression(String path, String contentHeaderType, String acceptHeaderEncoding,
                                          String expectedHeaderContentEncoding, int originalContentLength) {
        Response response = given()
                .when()
                .contentType(contentHeaderType)
                .header(HttpHeaders.ACCEPT_ENCODING, acceptHeaderEncoding)
                .get(path)
                .then()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_ENCODING, expectedHeaderContentEncoding)
                .extract().response();
        byte[] responseBody = response.getBody().asByteArray();
        int compressedContentLength = responseBody.length;
        assertTrue(compressedContentLength < originalContentLength);
    }

    private void testCompressedAndDecompressed(String compressionPath, String contentExpected) {
        Response response = given()
                .header(HttpHeaders.ACCEPT_ENCODING, BROTLI_ENCODING)
                .get(compressionPath)
                .then()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_ENCODING, BROTLI_ENCODING)
                .extract().response();
        byte[] compressedBytes = response.asByteArray();

        Response decompressionResponse = given()
                .header(HttpHeaders.CONTENT_ENCODING, BROTLI_ENCODING)
                .body(compressedBytes)
                .post("/compression/decompression")
                .then()
                .statusCode(200)
                .extract().response();
        assertThat(decompressionResponse.asString(), containsString(contentExpected));
    }

    private int calculateOriginalJsonLength() throws IOException {
        try (InputStream inputStream = getClass().getResourceAsStream("/sample.json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(inputStream);
            String jsonString = objectMapper.writeValueAsString(jsonNode);
            return jsonString.getBytes().length;
        }
    }

    private int calculateXmlLength() {
        return getLength("/sample.xml");
    }

    private int calculateTextLength(String fileName) {
        return getLength(fileName);
    }

    private int getLength(String file) {
        try {
            return IOUtils.toString(GreetingResourceTest.class.getResourceAsStream(file), StandardCharsets.UTF_8).length();
        } catch (IOException var2) {
            throw new AssertionError("Could not load file " + file + " . Caused by " + var2.getMessage());
        }
    }

}