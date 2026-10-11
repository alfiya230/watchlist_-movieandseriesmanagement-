
package com.movieseries;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import dao.MovieDAO;
import model.Movie;

@WebServlet("/MovieServlet")
public class MovieServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final MovieDAO movieDAO = new MovieDAO();
    private final Gson gson = new Gson();

    // Data received from the frontend
    private static class MovieRequest {
        String title;
        String genre;
        double rating;
        int releaseYear;
        int duration;
    }

    // Standard JSON response
    private static class ApiResponse {
        String status;
        String message;

        ApiResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
    }

    // Configure JSON response
    private void prepareJSON(HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    }

    // Send an error response
    private void sendError(
            HttpServletResponse response,
            int statusCode,
            String message) throws IOException {

        response.setStatus(statusCode);

        response.getWriter().write(
            gson.toJson(new ApiResponse("error", message))
        );
    }

    // GET: View all movies or search by title
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        prepareJSON(response);

        String action = request.getParameter("action");

        try {
            // Retrieve all movies
            if ("list".equalsIgnoreCase(action)) {
                List<Movie> movies = movieDAO.getAllMovies();

                response.getWriter().write(gson.toJson(movies));
                return;
            }

            // Search for a movie by title
            if ("search".equalsIgnoreCase(action)) {
                String title = request.getParameter("title");

                if (title == null || title.trim().isEmpty()) {
                    sendError(
                        response,
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Enter a movie title."
                    );
                    return;
                }

                Movie movie = movieDAO.searchMovie(title.trim());

                response.getWriter().write(gson.toJson(movie));
                return;
            }

            // Invalid action
            sendError(
                response,
                HttpServletResponse.SC_BAD_REQUEST,
                "Unknown action. Use action=list or action=search."
            );

        } catch (Exception e) {
            e.printStackTrace();

            sendError(
                response,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Unable to retrieve movies."
            );
        }
    }

    // POST: Add a new movie
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        prepareJSON(response);

        try (BufferedReader reader = request.getReader()) {

            MovieRequest data;

            try {
                data = gson.fromJson(reader, MovieRequest.class);
            } catch (JsonParseException e) {
                sendError(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid movie data."
                );
                return;
            }

            // Validate movie details
            if (data == null
                    || data.title == null
                    || data.title.trim().isEmpty()
                    || data.genre == null
                    || data.genre.trim().isEmpty()
                    || Double.isNaN(data.rating)
                    || Double.isInfinite(data.rating)
                    || data.rating < 0
                    || data.rating > 10
                    || data.releaseYear < 1888
                    || data.releaseYear > 2100
                    || data.duration <= 0) {

                sendError(
                    response,
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Enter a title, genre, rating from 0 to 10, "
                    + "valid release year, and positive duration."
                );
                return;
            }

            // Generate the next movie ID
            int nextId = 1;

            List<Movie> existingMovies = movieDAO.getAllMovies();

            if (existingMovies != null) {
                for (Movie existing : existingMovies) {
                    if (existing != null && existing.getId() >= nextId) {
                        nextId = existing.getId() + 1;
                    }
                }
            }

            // Create the movie object
            Movie newMovie = new Movie(
                nextId,
                data.title.trim(),
                data.genre.trim(),
                data.rating,
                data.releaseYear,
                data.duration
            );

            // Save the movie to Oracle through MovieDAO
            boolean saved = movieDAO.addMovie(newMovie);

            if (!saved) {
                sendError(
                    response,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Movie could not be saved. Check the database "
                    + "and server console."
                );
                return;
            }

            // Successful response
            response.setStatus(HttpServletResponse.SC_CREATED);

            response.getWriter().write(
                gson.toJson(
                    new ApiResponse(
                        "success",
                        "Movie added successfully."
                    )
                )
            );

        } catch (Exception e) {
            e.printStackTrace();

            sendError(
                response,
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                "Unable to process the movie."
            );
        }
    }
}
