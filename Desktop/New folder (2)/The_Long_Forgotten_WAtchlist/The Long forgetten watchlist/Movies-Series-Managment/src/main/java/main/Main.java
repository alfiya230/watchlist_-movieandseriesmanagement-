package main;

import java.util.Scanner;
import java.util.ArrayList;

import dao.MovieDAO;
import dao.SeriesDAO;
import dao.WatchlistDAO;

import model.Movie;
import model.Series;

import service.WatchlistService;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        WatchlistService service = new WatchlistService();

        MovieDAO movieDAO = new MovieDAO();
        SeriesDAO seriesDAO = new SeriesDAO();
        WatchlistDAO watchlistDAO = new WatchlistDAO();

        int choice;

        do {

            System.out.println("\n==========================================");
            System.out.println("       THE LONG FORGOTTEN WATCHLIST");
            System.out.println("==========================================");
            System.out.println("1.  Add Movie");
            System.out.println("2.  Add Series");
            System.out.println("3.  View All Movies and Series");
            System.out.println("4.  Search Movie/Series");
            System.out.println("5.  Sort Movies/Series");
            System.out.println("6.  Add to Watchlist");
            System.out.println("7.  Remove from Watchlist");
            System.out.println("8.  View Watchlist");
            System.out.println("9.  Mark as Watched");
            System.out.println("10. Update Movie");
            System.out.println("11. Update Series");
            System.out.println("12. Delete Movie");
            System.out.println("13. Delete Series");
            System.out.println("14. Exit");
            System.out.println("==========================================");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                // ==========================================
                // 1. ADD MOVIE
                // ==========================================

                case 1:

                    System.out.print("Enter movie ID: ");
                    int movieId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter title: ");
                    String movieTitle = scanner.nextLine();

                    System.out.print("Enter genre: ");
                    String movieGenre = scanner.nextLine();

                    System.out.print("Enter rating: ");
                    double movieRating = scanner.nextDouble();

                    System.out.print("Enter release year: ");
                    int movieYear = scanner.nextInt();

                    System.out.print("Enter duration in minutes: ");
                    int duration = scanner.nextInt();
                    scanner.nextLine();

                    Movie movie = new Movie(
                            movieId,
                            movieTitle,
                            movieGenre,
                            movieRating,
                            movieYear,
                            duration
                    );

                    movieDAO.addMovie(movie);

                    // Add to LinkedList, HashMap and Queue
                    service.addMedia(movie);

                    System.out.println("Movie added successfully.");

                    break;


                // ==========================================
                // 2. ADD SERIES
                // ==========================================

                case 2:

                    System.out.print("Enter series ID: ");
                    int seriesId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter title: ");
                    String seriesTitle = scanner.nextLine();

                    System.out.print("Enter genre: ");
                    String seriesGenre = scanner.nextLine();

                    System.out.print("Enter rating: ");
                    double seriesRating = scanner.nextDouble();

                    System.out.print("Enter release year: ");
                    int seriesYear = scanner.nextInt();

                    System.out.print("Enter number of seasons: ");
                    int seasons = scanner.nextInt();
                    scanner.nextLine();

                    Series series = new Series(
                            seriesId,
                            seriesTitle,
                            seriesGenre,
                            seriesRating,
                            seriesYear,
                            seasons
                    );

                    seriesDAO.addSeries(series);

                    // Add to LinkedList, HashMap and Queue
                    service.addMedia(series);

                    System.out.println("Series added successfully.");

                    break;


                // ==========================================
                // 3. VIEW ALL
                // ==========================================

                case 3:

                    ArrayList<Movie> movies = movieDAO.getAllMovies();

                    System.out.println("\n===== MOVIES =====");

                    if (movies.isEmpty()) {

                        System.out.println("No movies found in database.");

                    } else {

                        for (Movie m : movies) {

                            m.displayDetails();

                            System.out.println("--------------------");
                        }
                    }

                    ArrayList<Series> seriesList =
                            seriesDAO.getAllSeries();

                    System.out.println("\n===== SERIES =====");

                    if (seriesList.isEmpty()) {

                        System.out.println("No series found in database.");

                    } else {

                        for (Series s : seriesList) {

                            s.displayDetails();

                            System.out.println("--------------------");
                        }
                    }

                    break;


                // ==========================================
                // 4. SEARCH
                // ==========================================

                case 4:

                    System.out.print("Enter title to search: ");

                    String searchTitle =
                            scanner.nextLine().trim();

                    // Store search in Stack
                    service.addSearch(searchTitle);

                    boolean found = false;

                    ArrayList<Movie> searchMovies =
                            movieDAO.getAllMovies();

                    for (Movie m : searchMovies) {

                        if (m.getTitle()
                                .toLowerCase()
                                .contains(searchTitle.toLowerCase())) {

                            m.displayDetails();

                            System.out.println("--------------------");

                            found = true;
                        }
                    }

                    ArrayList<Series> searchSeries =
                            seriesDAO.getAllSeries();

                    for (Series s : searchSeries) {

                        if (s.getTitle()
                                .toLowerCase()
                                .contains(searchTitle.toLowerCase())) {

                            s.displayDetails();

                            System.out.println("--------------------");

                            found = true;
                        }
                    }

                    if (!found) {

                        System.out.println(
                                "No movie or series found."
                        );
                    }

                    break;


                // ==========================================
                // 5. SORT
                // ==========================================

                case 5:

                    System.out.println("\nSort By:");
                    System.out.println("1. Title");
                    System.out.println("2. Rating");
                    System.out.println("3. Release Year");

                    System.out.print("Enter choice: ");

                    int sortChoice = scanner.nextInt();
                    scanner.nextLine();

                    ArrayList<Movie> sortMovies =
                            movieDAO.getAllMovies();

                    ArrayList<Series> sortSeries =
                            seriesDAO.getAllSeries();

                    switch (sortChoice) {

                        case 1:

                            sortMovies.sort(
                                    (m1, m2) ->
                                            m1.getTitle()
                                                    .compareToIgnoreCase(
                                                            m2.getTitle()
                                                    )
                            );

                            sortSeries.sort(
                                    (s1, s2) ->
                                            s1.getTitle()
                                                    .compareToIgnoreCase(
                                                            s2.getTitle()
                                                    )
                            );

                            break;


                        case 2:

                            sortMovies.sort(
                                    (m1, m2) ->
                                            Double.compare(
                                                    m2.getRating(),
                                                    m1.getRating()
                                            )
                            );

                            sortSeries.sort(
                                    (s1, s2) ->
                                            Double.compare(
                                                    s2.getRating(),
                                                    s1.getRating()
                                            )
                            );

                            break;


                        case 3:

                            sortMovies.sort(
                                    (m1, m2) ->
                                            Integer.compare(
                                                    m2.getReleaseYear(),
                                                    m1.getReleaseYear()
                                            )
                            );

                            sortSeries.sort(
                                    (s1, s2) ->
                                            Integer.compare(
                                                    s2.getReleaseYear(),
                                                    s1.getReleaseYear()
                                            )
                            );

                            break;


                        default:

                            System.out.println(
                                    "Invalid sorting choice."
                            );

                            break;
                    }

                    System.out.println("\n===== SORTED MOVIES =====");

                    for (Movie m : sortMovies) {

                        m.displayDetails();

                        System.out.println("--------------------");
                    }

                    System.out.println("\n===== SORTED SERIES =====");

                    for (Series s : sortSeries) {

                        s.displayDetails();

                        System.out.println("--------------------");
                    }

                    break;


                // ==========================================
                // 6. ADD TO WATCHLIST
                // ==========================================

                case 6:

                    System.out.print("Enter Watchlist ID: ");
                    int watchlistId = scanner.nextInt();

                    System.out.print(
                            "Enter Movie ID (0 if series): "
                    );

                    int watchMovieId = scanner.nextInt();

                    System.out.print(
                            "Enter Series ID (0 if movie): "
                    );

                    int watchSeriesId = scanner.nextInt();

                    scanner.nextLine();

                    if (watchMovieId == 0 &&
                            watchSeriesId == 0) {

                        System.out.println(
                                "Enter either Movie ID or Series ID."
                        );

                    } else if (watchMovieId != 0 &&
                            watchSeriesId != 0) {

                        System.out.println(
                                "Enter only Movie ID OR Series ID."
                        );

                    } else {

                        watchlistDAO.addToWatchlist(
                                watchlistId,
                                watchMovieId,
                                watchSeriesId
                        );
                    }

                    break;


                // ==========================================
                // 7. REMOVE FROM WATCHLIST
                // ==========================================

                case 7:

                    System.out.print(
                            "Enter Watchlist ID to remove: "
                    );

                    int removeId = scanner.nextInt();
                    scanner.nextLine();

                    watchlistDAO.removeFromWatchlist(removeId);

                    break;


                // ==========================================
                // 8. VIEW WATCHLIST
                // ==========================================

                case 8:

                    watchlistDAO.displayWatchlist();

                    break;


                // ==========================================
                // 9. MARK AS WATCHED
                // ==========================================

                case 9:

                    System.out.print(
                            "Enter Watchlist ID to mark as watched: "
                    );

                    int watchedId = scanner.nextInt();
                    scanner.nextLine();

                    watchlistDAO.markAsWatched(watchedId);

                    break;


                // ==========================================
                // 10. UPDATE MOVIE
                // ==========================================

                case 10:

                    System.out.print(
                            "Enter movie ID to update: "
                    );

                    int updateMovieId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter new title: ");
                    String updateTitle = scanner.nextLine();

                    System.out.print("Enter new genre: ");
                    String updateGenre = scanner.nextLine();

                    System.out.print("Enter new rating: ");
                    double updateRating = scanner.nextDouble();

                    System.out.print(
                            "Enter new release year: "
                    );

                    int updateYear = scanner.nextInt();

                    System.out.print(
                            "Enter new duration: "
                    );

                    int updateDuration = scanner.nextInt();
                    scanner.nextLine();

                    Movie updatedMovie = new Movie(
                            updateMovieId,
                            updateTitle,
                            updateGenre,
                            updateRating,
                            updateYear,
                            updateDuration
                    );

                    movieDAO.updateMovie(updatedMovie);

                    break;


                // ==========================================
                // 11. UPDATE SERIES
                // ==========================================

                case 11:

                    System.out.print(
                            "Enter series ID to update: "
                    );

                    int updateSeriesId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter new title: ");
                    String updateSeriesTitle =
                            scanner.nextLine();

                    System.out.print("Enter new genre: ");
                    String updateSeriesGenre =
                            scanner.nextLine();

                    System.out.print("Enter new rating: ");
                    double updateSeriesRating =
                            scanner.nextDouble();

                    System.out.print(
                            "Enter new release year: "
                    );

                    int updateSeriesYear =
                            scanner.nextInt();

                    System.out.print(
                            "Enter new number of seasons: "
                    );

                    int updateSeasons =
                            scanner.nextInt();

                    scanner.nextLine();

                    Series updatedSeries = new Series(
                            updateSeriesId,
                            updateSeriesTitle,
                            updateSeriesGenre,
                            updateSeriesRating,
                            updateSeriesYear,
                            updateSeasons
                    );

                    seriesDAO.updateSeries(updatedSeries);

                    break;


                // ==========================================
                // 12. DELETE MOVIE
                // ==========================================

                case 12:

                    System.out.print(
                            "Enter movie ID to delete: "
                    );

                    int deleteMovieId = scanner.nextInt();
                    scanner.nextLine();

                    movieDAO.deleteMovie(deleteMovieId);

                    break;


                // ==========================================
                // 13. DELETE SERIES
                // ==========================================

                case 13:

                    System.out.print(
                            "Enter series ID to delete: "
                    );

                    int deleteSeriesId = scanner.nextInt();
                    scanner.nextLine();

                    seriesDAO.deleteSeries(deleteSeriesId);

                    break;


                // ==========================================
                // 14. EXIT
                // ==========================================

                case 14:

                    System.out.println(
                            "Thank you for using "
                            + "The Long Forgotten Watchlist."
                    );

                    break;


                default:

                    System.out.println(
                            "Invalid choice."
                    );

                    break;
            }

        } while (choice != 14);

        scanner.close();
    }
}