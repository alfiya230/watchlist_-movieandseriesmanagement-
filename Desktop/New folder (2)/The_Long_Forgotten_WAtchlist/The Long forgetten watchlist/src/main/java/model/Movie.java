package model;

public class Movie extends Media {

    private int duration;

    public Movie(int id, String title, String genre, double rating, int releaseYear, int duration) {
        super(id, title, genre, rating, releaseYear);
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void displayDetails() {
        System.out.println("Movie ID: " + getId());
        System.out.println("Title: " + getTitle());
        System.out.println("Genre: " + getGenre());
        System.out.println("Rating: " + getRating());
        System.out.println("Release Year: " + getReleaseYear());
        System.out.println("Duration: " + duration + " minutes");
        System.out.println("Watched: " + (isWatched() ? "Yes" : "No"));
    }
}