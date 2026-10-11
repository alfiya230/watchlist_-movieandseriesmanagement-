package dao;
import dao.WatchlistDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import db.DBconnection;
import model.Movie;

public class MovieDAO {

    public void addMovie(Movie movie) {

        String sql = "insert into movies values (?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DBconnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, movie.getId());
            statement.setString(2, movie.getTitle());
            statement.setString(3, movie.getGenre());
            statement.setDouble(4, movie.getRating());
            statement.setInt(5, movie.getReleaseYear());
            statement.setInt(6, movie.getDuration());

            statement.executeUpdate();

            System.out.println("Movie saved to database.");

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error saving movie.");
            e.printStackTrace();
        }
        
    }
    public ArrayList<Movie> getAllMovies() {

        ArrayList<Movie> movies = new ArrayList<>();

        String sql = "select * from movies";

        try {
            Connection connection = DBconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {

                Movie movie = new Movie(
                    result.getInt("movie_id"),
                    result.getString("title"),
                    result.getString("genre"),
                    result.getDouble("rating"),
                    result.getInt("release_year"),
                    result.getInt("duration")
                );

                movies.add(movie);
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error retrieving movies.");
            e.printStackTrace();
        }

        return movies;
    }
    public void updateMovie(Movie movie) {

        String sql = "update movies set title = ?, genre = ?, rating = ?, release_year = ?, duration = ? where movie_id = ?";

        try {
            Connection connection = DBconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, movie.getTitle());
            statement.setString(2, movie.getGenre());
            statement.setDouble(3, movie.getRating());
            statement.setInt(4, movie.getReleaseYear());
            statement.setInt(5, movie.getDuration());
            statement.setInt(6, movie.getId());

            statement.executeUpdate();
            
            System.out.println("Movie updated successfully.");

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error updating movie.");
            e.printStackTrace();
        }
    }
    
    public void deleteMovie(int movieId) {
    	WatchlistDAO watchlistDAO = new WatchlistDAO();
    	watchlistDAO.deleteByMovieId(movieId);
        String sql = "delete from movies where movie_id = ?";

        try {
            Connection connection = DBconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, movieId);

            statement.executeUpdate();

            System.out.println("Movie deleted successfully.");

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error deleting movie.");
            e.printStackTrace();
        }
    }
   
    public Movie searchMovie(String title) {
        String sql = "select * from movies where lower(title) = lower(?)";

        try {
            Connection connection = DBconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            System.out.println("Movie search query running...");
            statement.setString(1, title);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Movie movie = new Movie(
                    result.getInt("movie_id"),
                    result.getString("title"),
                    result.getString("genre"),
                    result.getDouble("rating"),
                    result.getInt("release_year"),
                    result.getInt("duration")
                );

                result.close();
                statement.close();
                connection.close();

                return movie;
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error searching movie.");
            e.printStackTrace();
        }

        return null;
    }
    public void deleteByMovieId(int movieId) {
        String sql = "delete from watchlist where movie_id = ?";

        try {
            Connection connection = DBconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, movieId);
            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error deleting movie from watchlist.");
            e.printStackTrace();
        }
    }
}