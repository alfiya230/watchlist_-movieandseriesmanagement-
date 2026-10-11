package db;

import dao.SeriesDAO;
import model.Series;
import java.util.ArrayList;

public class TestConnection {

    public static void main(String[] args) {

        SeriesDAO seriesDAO = new SeriesDAO();

        ArrayList<Series> seriesList = seriesDAO.getAllSeries();

        for (Series series : seriesList) {
            series.displayDetails();
            System.out.println("--------------------");
        }
    }
}