package com.redhat.qe;
import io.quarkus.qute.Location;
import io.quarkus.qute.Qute;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.Arrays;

import static java.util.Objects.requireNonNull;

@Path("")
public class Application {

    @Location("basic.html")
    Template template;

    @GET
    @Path("/test")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance located() {
        return addData(template);
    }

    private TemplateInstance addData(Template template) {
        return template.data("server", "Quarkus");
    }
}
