package org.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello")
public class HelloWorldResource {  // Renamed (not a Servlet)
    @GET
    @Produces("application/json")
    public String getSaludo() {
        return "{ \"mensaje\": \"Hola mundo\" }";
    }
}