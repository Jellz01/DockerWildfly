package org.example;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;

import javax.ws.rs.core.MediaType;
import java.sql.*;
import java.util.Collections;
import java.util.Map;

@Path("/hello")
public class HelloWorldResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastMessage() {
        String url = "jdbc:postgresql://josephdb:5432/mydb";
        String user = "myuser";
        String password = "mypassword";;

        String sql = "SELECT texto, fecha FROM MENSAJE ORDER BY id DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String texto = rs.getString("texto");
                Timestamp fecha = rs.getTimestamp("fecha");

                String json = String.format("{\"mensaje\": \"%s\", \"fecha\": \"%s\"}", texto, fecha.toString());
                ResponseBuilder responseBuilder = Response.ok(json);
                addCorsHeaders(responseBuilder);
                return responseBuilder.build();
            } else {
                String json = "{\"mensaje\": \"No hay mensajes en la base de datos.\"}";
                ResponseBuilder responseBuilder = Response.ok(json);
                addCorsHeaders(responseBuilder);
                return responseBuilder.build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            String errorJson = "{\"error\": \"Error al acceder a la base de datos.\"}";
            return Response.status(500).entity(errorJson).build();
        }
    }


    @POST
    @Path("/message")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response receiveMessage(Map<String, String> payload) {
        String mensaje = payload.get("message");

         String url = "jdbc:postgresql://josephdb:5432/mydb";
         String user = "myuser";
         String password = "mypassword";

        String insertSQL = "INSERT INTO MENSAJE (texto, fecha) VALUES (?, CURRENT_TIMESTAMP)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, mensaje);
            pstmt.executeUpdate();

            return Response.ok(Collections.singletonMap("status", "Mensaje guardado")).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(500).entity(Collections.singletonMap("error", "Error en la base de datos")).build();
        }
    }


    @OPTIONS
    @Path("/message")
    public Response handlePreflight() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }

    private void addCorsHeaders(ResponseBuilder responseBuilder) {
        responseBuilder.header("Access-Control-Allow-Origin", "*");
        responseBuilder.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        responseBuilder.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        responseBuilder.header("Access-Control-Allow-Credentials", "true");
    }
}
