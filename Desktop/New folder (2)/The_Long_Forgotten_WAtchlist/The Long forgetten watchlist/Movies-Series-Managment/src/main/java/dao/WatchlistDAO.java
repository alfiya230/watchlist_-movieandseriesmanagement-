package dao;
import java.sql.Connection;

import java.sql.PreparedStatement;

import db.DBconnection;
import java.sql.ResultSet;
import java.sql.SQLException;
public class WatchlistDAO {
	public void addToWatchlist(int watchlistId, int movieId, int seriesId) {

	    String sql = "insert into watchlist (watchlist_id, movie_id, series_id) values (?, ?, ?)";

	    try {
	        Connection connection = DBconnection.getConnection();

	        PreparedStatement statement = connection.prepareStatement(sql);

	        statement.setInt(1, watchlistId);
	        if (movieId == 0) {
	            statement.setNull(2, java.sql.Types.INTEGER);
	        } else {
	            statement.setInt(2, movieId);
	        }
	        if (seriesId == 0) {
	            statement.setNull(3, java.sql.Types.INTEGER);
	        } else {
	            statement.setInt(3, seriesId);
	        }
	        statement.executeUpdate();

	        System.out.println("Added to watchlist.");

	        statement.close();
	        connection.close();

	       
	    } catch (Exception e) {
	        System.out.println("Error adding to watchlist.");
	        e.printStackTrace();
	    }
	}
	public void markAsWatched(int watchlistId) {

	    String sql = "update watchlist set watched = 1 where watchlist_id = ?";

	    try {
	        Connection connection = DBconnection.getConnection();

	        PreparedStatement statement = connection.prepareStatement(sql);

	        statement.setInt(1, watchlistId);

	        statement.executeUpdate();

	        System.out.println("Marked as watched.");

	        statement.close();
	        connection.close();

	    } catch (Exception e) {
	        System.out.println("Error marking as watched.");
	        e.printStackTrace();
	    }
	}
	public void removeFromWatchlist(int watchlistId) {

	    String sql = "delete from watchlist where watchlist_id = ?";

	    try {
	        Connection connection = DBconnection.getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql);

	        statement.setInt(1, watchlistId);
	        statement.executeUpdate();

	        System.out.println("Removed from watchlist.");

	        statement.close();
	        connection.close();

	    } catch (Exception e) {
	        System.out.println("Error removing from watchlist.");
	        e.printStackTrace();
	    }
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
	public void deleteBySeriesId(int seriesId) {
	    String sql = "delete from watchlist where series_id = ?";

	    try {
	        Connection connection = DBconnection.getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql);

	        statement.setInt(1, seriesId);
	        statement.executeUpdate();

	        statement.close();
	        connection.close();

	    } catch (Exception e) {
	        System.out.println("Error deleting series from watchlist.");
	        e.printStackTrace();
	    }
	}
	public void displayWatchlist() {
	    String sql = "select w.watchlist_id, m.title as movie_title, s.title as series_title, w.watched " +
	                 "from watchlist w " +
	                 "left join movies m on w.movie_id = m.movie_id " +
	                 "left join series s on w.series_id = s.series_id";

	    try {
	        Connection connection = DBconnection.getConnection();
	        PreparedStatement statement = connection.prepareStatement(sql);
	        ResultSet result = statement.executeQuery();

	        while (result.next()) {
	            System.out.println("Watchlist ID: " + result.getInt("watchlist_id"));

	            if (result.getString("movie_title") != null) {
	                System.out.println("Movie: " + result.getString("movie_title"));
	            }

	            if (result.getString("series_title") != null) {
	                System.out.println("Series: " + result.getString("series_title"));
	            }

	            System.out.println("Watched: " + (result.getInt("watched") == 1 ? "Yes" : "No"));
	            System.out.println("--------------------");
	        }

	        result.close();
	        statement.close();
	        connection.close();

	    } catch (Exception e) {
	        System.out.println("Error displaying watchlist.");
	        e.printStackTrace();
	    }
	}
}