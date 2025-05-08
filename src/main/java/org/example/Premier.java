package org.example;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/football")
public class Premier {

    // Define the Team class
    public static class Team {
        private String teamName;
        private String city;
        private String manager;
        private String stadium;
        private int foundedYear;
        private String website;

        // Constructor
        public Team(String teamName, String city, String manager, String stadium, int foundedYear, String website) {
            this.teamName = teamName;
            this.city = city;
            this.manager = manager;
            this.stadium = stadium;
            this.foundedYear = foundedYear;
            this.website = website;
        }

        // Getters
        public String getTeamName() { return teamName; }
        public String getCity() { return city; }
        public String getManager() { return manager; }
        public String getStadium() { return stadium; }
        public int getFoundedYear() { return foundedYear; }
        public String getWebsite() { return website; }
    }

    // Method to fetch Premier League teams from the database
    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();

        // SQL query to fetch Premier League teams
        String query = "SELECT * FROM premier_league_teams;";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Mapping the result set to a Team object
                Team team = new Team(
                        rs.getString("team_name"),
                        rs.getString("city"),
                        rs.getString("manager"),
                        rs.getString("stadium"),
                        rs.getInt("founded_year"),
                        rs.getString("website")
                );
                teams.add(team);
            }

            // If no teams were found, throw an exception
            if (teams.isEmpty()) {
                throw new SQLException("No Premier League teams found.");
            }

        } catch (SQLException e) {
            // Log the exception
            e.printStackTrace();
            throw new RuntimeException("Error fetching Premier League teams: " + e.getMessage());
        }

        return teams;  // Return the list of teams
    }



    // Endpoint to fetch football teams as a JSON response
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFootballTeams() {
        try {
            List<Team> teams = getTeams(); // Call the method to fetch teams

            // If teams are found, return them as a JSON response
            return Response.ok(teams).build();
        } catch (RuntimeException e) {
            // Return a 404 Not Found response with an error message if no teams are found
            String errorMessage = "{ \"error\": \"No Premier League teams found.\" }";
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessage)
                    .build();
        } catch (Exception e) {
            // Return a 500 Internal Server Error response if there is any other exception
            String errorMessage = "{ \"error\": \"Error fetching Premier League teams: " + e.getMessage() + "\" }";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorMessage)
                    .build();
        }
    }

    @OPTIONS
    public Response handlePreflight() {
        // Handle preflight request by responding with OK and the necessary headers
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }
}
