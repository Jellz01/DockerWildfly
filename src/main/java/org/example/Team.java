package org.example;

public class Team {

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

    // Getters and setters
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public void setFoundedYear(int foundedYear) {
        this.foundedYear = foundedYear;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
