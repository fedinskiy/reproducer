package io.quarkus.qe;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;

@Path("/compression")
public class Brotli4JResource {

    public final static String DEFAULT_TEXT_PLAIN = "But donkeys, I fear, will never be abolished.";

    @Inject
    Brotli4JRestMock brotli4JRestMock;

    @GET
    @Path("/brotli/json")
    @Produces(MediaType.APPLICATION_JSON)
    public HashMap<String, Object> jsonHttpCompressionResponse() {
        return brotli4JRestMock.returnResponse(Brotli4JRestMock.ResponseType.JSON);
    }

    @GET
    @Path("/brotli/xml")
    @Produces(MediaType.APPLICATION_XML)
    public String xmlHttpCompressionResponse() {
        return brotli4JRestMock.returnResponse(Brotli4JRestMock.ResponseType.XML).get("xml").toString();
    }

    @POST
    @Path("/decompression")
    @Produces(MediaType.TEXT_PLAIN)
    public String decompressionHttpResponse(byte[] compressedData) {
        String decompressedData = new String(compressedData);
        System.out.println(decompressedData);
        return decompressedData;
    }

    @GET
    @Path("/default/text")
    @Produces(MediaType.TEXT_PLAIN)
    public String textPlainDefaultHttpCompressionResponse() {
        return DEFAULT_TEXT_PLAIN;
    }

    @GET
    @Path("/text")
    @Produces(MediaType.TEXT_PLAIN)
    public String textPlainHttpCompressionResponse() {
        return brotli4JRestMock.returnTextPlainResponse(Brotli4JRestMock.ResponseType.TEXT);
    }

    @GET
    @Path("/text/big")
    @Produces(MediaType.TEXT_PLAIN)
    public String textPlainBigHttpCompressionResponse() {
        return brotli4JRestMock.returnTextPlainResponse(Brotli4JRestMock.ResponseType.BIG_TEXT);
    }

}
