package model;

import java.util.ArrayList;

public class Watchlist {

    private ArrayList<Media> mediaList;

    public Watchlist() {
        mediaList = new ArrayList<>();
    }

    public void addMedia(Media media) {
        mediaList.add(media);
    }

    public void removeMedia(int id) {
        mediaList.removeIf(media -> media.getId() == id);
    }

    public void displayWatchlist() {
        if (mediaList.isEmpty()) {
            System.out.println("Watchlist is empty.");
            return;
        }

        for (Media media : mediaList) {
            media.displayDetails();
            System.out.println("--------------------");
        }
    }

    public ArrayList<Media> getMediaList() {
        return mediaList;
    }
}