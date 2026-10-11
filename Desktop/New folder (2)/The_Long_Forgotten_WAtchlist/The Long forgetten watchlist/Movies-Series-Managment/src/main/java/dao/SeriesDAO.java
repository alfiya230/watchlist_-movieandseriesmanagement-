package dao;

import java.sql.Connection;
import dao.WatchlistDAO;
import java.sql.PreparedStatement;

import db.DBconnection;
import model.Series;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SeriesDAO {

    public void addSeries(Series series) {

        String sql = "insert into series values (?, ?, ?, ?, ?, ?)";

        try {
            Connection connection = DBconnection.getConnection();

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, series.getId());
            statement.setString(2, series.getTitle());
            statement.setString(3, series.getGenre());
            statement.setDouble(4, series.getRating());
            statement.setInt(5, series.getReleaseYear());
            statement.setInt(6, series.getSeasons());

            statement.executeUpdate();

            System.out.println("Series saved to database.");

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error saving series.");
            e.printStackTrace();
        }
    }
    public ArrayList<Series> getAllSeries() {

        ArrayList<Series> seriesList = new ArrayList<>();

        String sql = "select * from series";

        try {
            Connection connection = DBconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            while (result.next()) {

                Series series = new Series(
                    result.getInt("series_id"),
                    result.getString("title"),
                    result.getString("genre"),
                    result.getDouble("rating"),
                    result.getInt("release_year"),
                    result.getInt("seasons")
                );

                seriesList.add(series);
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error retrieving series.");
            e.printStackTrace();
        }

        return seriesList;
    }
    public void deleteSeries(int seriesId) {
    	WatchlistDAO watchlistDAO = new WatchlistDAO();
    	watchlistDAO.deleteBySeriesId(seriesId);

        String sql = "delete from series where series_id = ?";

        try {
          
            Connection connection = DBconnection.getConnection();

          

            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, seriesId);

           
            statement.executeUpdate();

            System.out.println("Series deleted successfully.");

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error deleting series.");
            e.printStackTrace();
        }
    }
    public Series searchSeries(String title) {
        String sql = "select * from series where lower(title) = lower(?)";

        try {
            Connection connection = DBconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, title);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                Series series = new Series(
                    result.getInt("series_id"),
                    result.getString("title"),
                    result.getString("genre"),
                    result.getDouble("rating"),
                    result.getInt("release_year"),
                    result.getInt("seasons")
                );

                result.close();
                statement.close();
                connection.close();

                return series;
            }

            result.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error searching series.");
            e.printStackTrace();
        }

        return null;
    }
    public void updateSeries(Series series) {
        String sql = "update series set title = ?, genre = ?, rating = ?, release_year = ?, seasons = ? where series_id = ?";

        try {
            Connection connection = DBconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, series.getTitle());
            statement.setString(2, series.getGenre());
            statement.setDouble(3, series.getRating());
            statement.setInt(4, series.getReleaseYear());
            statement.setInt(5, series.getSeasons());
            statement.setInt(6, series.getId());

            statement.executeUpdate();

            System.out.println("Series updated successfully.");

            statement.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("Error updating series.");
            e.printStackTrace();
        }
    }
}
