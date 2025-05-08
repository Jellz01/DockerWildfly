package org.example;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/")
public class OptionsResource {

    @OPTIONS
    @Path("{path: .*}")
    public Response handleOptions() {
        return Response.ok().build(); // 200 OK for any OPTIONS
    }
}
