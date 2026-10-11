package model;

public class Series extends Media {

    private int seasons;

    public Series(int id, String title, String genre, double rating, int releaseYear, int seasons) {
        super(id, title, genre, rating, releaseYear);
        this.seasons = seasons;
    }

    public int getSeasons() {
        return seasons;
    }

    @Override
    public void displayDetails() {
        System.out.println("Series ID: " + getId());
        System.out.println("Title: " + getTitle());
        System.out.println("Genre: " + getGenre());
        System.out.println("Rating: " + getRating());
        System.out.println("Release Year: " + getReleaseYear());
        System.out.println("Seasons: " + seasons);
        System.out.println("Watched: " + (isWatched() ? "Yes" : "No"));
    }
}