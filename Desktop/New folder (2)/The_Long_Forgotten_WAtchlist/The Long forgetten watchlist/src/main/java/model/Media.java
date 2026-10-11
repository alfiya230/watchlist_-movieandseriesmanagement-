package model;

public abstract class Media {

    private int id;
    private String title;
    private String genre;
    private double rating;
    private int releaseYear;
    private boolean watched;

    public Media(int id, String title, String genre, double rating, int releaseYear) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.watched = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public boolean isWatched() {
        return watched;
    }

    public void markAsWatched() {
        watched = true;
    }

    public abstract void displayDetails();
}