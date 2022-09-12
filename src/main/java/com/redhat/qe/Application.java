package com.redhat.qe;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import static java.util.Objects.requireNonNull;

@Path("")
public class Application {

    @Inject
    Template basic;

    @Location("basic.html")
    Template located;

    @GET
    @Path("/base")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance base() {
        return addData(basic);
    }

    @GET
    @Path("/located")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance located() {
        return addData(located);
    }

    private TemplateInstance addData(Template template) {
        return template.data("server", "Quarkus");
    }
}
