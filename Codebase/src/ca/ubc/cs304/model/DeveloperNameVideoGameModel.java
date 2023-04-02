package ca.ubc.cs304.model;

public class DeveloperNameVideoGameModel {

    private final String lead_developer;
    private final String website;
    private final String developer_name;

    private final String genre;

    private final String title;

    private final int year;

    public DeveloperNameVideoGameModel(String title, int year, String genre, String lead_developer, String website, String developer_name) {
        this.lead_developer = lead_developer;
        this.website = website;
        this.developer_name = developer_name;
        this.genre = genre;
        this.title = title;
        this.year = year;
    }

    public String getLeadDeveloper() {
        return lead_developer;
    }

    public String getWebsite() {
        return website;
    }

    public String getDeveloperName() {
        return developer_name;
    }

    public String getTitle() { return title; }

    public int getYear() { return year; }

    public String getGenre() { return genre; }
}