package ca.ubc.cs304.model;

public class VideoGameCountModel {
    private final String title;
    private final int year;
    private final String genre;
    private final String developer_name;
    private final int columnNum;

    public VideoGameCountModel(String title, int year, String genre, String developer_name, int columnNum) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.developer_name = developer_name;
        this.columnNum = columnNum;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public String getDeveloperName() {
        return developer_name;
    }

    public int getColumnNum() { return columnNum; }
}
