package org.oddjob.boot.config;

import org.springframework.stereotype.Controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.Objects;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 *
 */
@Controller
@Path("")
public class StaticController {


    @GET
    @Path("{path:.*}")
    public Response get(@PathParam("path") String path) {

        if ("".equals(path)) {
            path = "index.html";
        }

        InputStream resource = getClass().getClassLoader()
                .getResourceAsStream("org/oddjob/webapp/" + path);

        return Objects.isNull(resource)
                ? Response.status(NOT_FOUND).build()
                : Response.ok().entity(resource).build();

    }
}
