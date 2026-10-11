
package web;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.MovieDAO;
import model.Movie;

@WebServlet("/api/movies")
public class MovieServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final MovieDAO movieDAO = new MovieDAO();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
                         throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ArrayList<Movie> movies = movieDAO.getAllMovies();

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(movies));
    }
}
