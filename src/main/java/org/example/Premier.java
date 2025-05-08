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

    // Clase interna para representar un equipo
    public static class Team {
        private String teamName;
        private String city;
        private String manager;
        private String stadium;
        private int foundedYear;
        private String website;

        public Team(String teamName, String city, String manager, String stadium, int foundedYear, String website) {
            this.teamName = teamName;
            this.city = city;
            this.manager = manager;
            this.stadium = stadium;
            this.foundedYear = foundedYear;
            this.website = website;
        }

        public String getTeamName() { return teamName; }
        public String getCity() { return city; }
        public String getManager() { return manager; }
        public String getStadium() { return stadium; }
        public int getFoundedYear() { return foundedYear; }
        public String getWebsite() { return website; }
    }

    // Inicializa la base de datos y llena los datos si no existen
    private void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS premier_league_teams (id SERIAL PRIMARY KEY, team_name VARCHAR(100) NOT NULL, city VARCHAR(100), manager VARCHAR(100), stadium VARCHAR(100), founded_year INT, website VARCHAR(255));";

        String insertSQL = "INSERT INTO premier_league_teams (team_name, city, manager, stadium, founded_year, website) SELECT * FROM (VALUES " +
                "('Manchester United', 'Manchester', 'Erik ten Hag', 'Old Trafford', 1878, 'https://www.manutd.com')," +
                "('Liverpool', 'Liverpool', 'Jürgen Klopp', 'Anfield', 1892, 'https://www.liverpoolfc.com')," +
                "('Chelsea', 'London', 'Graham Potter', 'Stamford Bridge', 1905, 'https://www.chelseafc.com')," +
                "('Arsenal', 'London', 'Mikel Arteta', 'Emirates Stadium', 1886, 'https://www.arsenal.com')," +
                "('Manchester City', 'Manchester', 'Pep Guardiola', 'Etihad Stadium', 1880, 'https://www.mancity.com')," +
                "('Tottenham Hotspur', 'London', 'Antonio Conte', 'Tottenham Hotspur Stadium', 1882, 'https://www.tottenhamhotspur.com')," +
                "('Leeds United', 'Leeds', 'Jesse Marsch', 'Elland Road', 1919, 'https://www.leedsunited.com')," +
                "('West Ham United', 'London', 'David Moyes', 'London Stadium', 1895, 'https://www.whufc.com')," +
                "('Aston Villa', 'Birmingham', 'Steven Gerrard', 'Villa Park', 1874, 'https://www.avfc.co.uk')," +
                "('Everton', 'Liverpool', 'Frank Lampard', 'Goodison Park', 1878, 'https://www.evertonfc.com')" +
                ") AS tmp(team_name, city, manager, stadium, founded_year, website) WHERE NOT EXISTS (SELECT 1 FROM premier_league_teams);";

        String query = "SELECT * FROM premier_league_teams;";


        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);
            stmt.executeUpdate(insertSQL);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing Premier League DB: " + e.getMessage());
        }
    }

    // Obtiene la lista de equipos desde la base de datos
    public List<Team> getTeams() {
        initializeDatabase();
        List<Team> teams = new ArrayList<>();

        String query = "SELECT * FROM premier_league_teams;";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
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

            if (teams.isEmpty()) {
                throw new SQLException("No Premier League teams found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching Premier League teams: " + e.getMessage());
        }

        return teams;
    }

    // Endpoint GET para obtener equipos
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFootballTeams() {
        try {
            List<Team> teams = getTeams();
            return Response.ok(teams).build();
        } catch (RuntimeException e) {
            String errorMessage = "{ \"error\": \"No Premier League teams found.\" }";
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorMessage)
                    .build();
        } catch (Exception e) {
            String errorMessage = "{ \"error\": \"Error fetching Premier League teams: " + e.getMessage() + "\" }";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorMessage)
                    .build();
        }
    }

    // Endpoint OPTIONS para CORS preflight
    @OPTIONS
    public Response handlePreflight() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }
}
