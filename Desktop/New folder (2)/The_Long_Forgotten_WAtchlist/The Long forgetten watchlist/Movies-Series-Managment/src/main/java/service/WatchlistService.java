package service;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.Stack;
import java.util.Queue;
import model.Media;

public class WatchlistService {

	private LinkedList<Media> mediaList;
	private HashMap<Integer, Media> mediaMap;
	private Stack<String> searchHistory;
	private Queue<Media> recentlyAdded;

	public WatchlistService() {

	    mediaList = new LinkedList<>();
	    mediaMap = new HashMap<>();
	    searchHistory = new Stack<>();
	    recentlyAdded = new LinkedList<>();
	}
	
	public void addMedia(Media media) {

	    mediaList.add(media);

	    mediaMap.put(media.getId(), media);

	    recentlyAdded.offer(media);
	}
	
	public Media findById(int id) {
        return mediaMap.get(id);
    }
	
	public void addSearch(String search) {
	    searchHistory.push(search);
	}

    public void displayAllMedia() {
        if (mediaList.isEmpty()) {
            System.out.println("No movies or series available.");
            return;
        }
        for (Media media : mediaList) {
            media.displayDetails();
            System.out.println("--------------------");
        }
    }
    
    public void displayRecentlyAdded() {

        if (recentlyAdded.isEmpty()) {
            System.out.println("No recently added media.");
            return;
        }

        System.out.println("\n===== RECENTLY ADDED =====");

        for (Media media : recentlyAdded) {
            media.displayDetails();
            System.out.println("--------------------");
        }
    }
    
    public void displaySearchHistory() {

        if (searchHistory.isEmpty()) {
            System.out.println("No search history.");
            return;
        }

        System.out.println("\n===== SEARCH HISTORY =====");

        for (int i = searchHistory.size() - 1; i >= 0; i--) {
            System.out.println(searchHistory.get(i));
        }
    }

    public Media search(String title) {

        for (Media media : mediaList) {

            if (media.getTitle()
                     .toLowerCase()
                     .contains(title.toLowerCase())) {

                return media;
            }
        }

        return null;
    }

    public Media search(String title, String genre) {
        for (Media media : mediaList) {
            if (media.getTitle().equalsIgnoreCase(title)
                    && media.getGenre().equalsIgnoreCase(genre)) {
                return media;
            }
        }

        return null;
    }

    public LinkedList<Media> getMediaList() {
        return mediaList;
    }
}