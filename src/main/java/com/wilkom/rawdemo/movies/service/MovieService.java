package com.wilkom.rawdemo.movies.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.wilkom.rawdemo.movies.data.MovieDataSource;
import com.wilkom.rawdemo.movies.model.Movie;

@Service
public class MovieService {

    private MovieDataSource movieDataSource = null;

    public MovieService() {
        movieDataSource = MovieDataSource.getInstance();
    }

    /**
     * Returns a Set of Studios present in this DataSource
     *
     * @return A Set of String objects
     */
    public Set<String> getStudios() {
        // CODE1:Return a Set of Studios present in this DataSource
        return movieDataSource.getStudios();
    }

    /**
     * Get all Movies of a single Studio
     *
     * @param studio
     *               The name of the movie studio
     * @return A List of zero or more movies. (Empty list is returned when
     *         studio is not found)
     */
    public List<Movie> getMoviesByStudio(String studio) {
        // CODE2:Return a List of zero or more movies. Empty list is returned when
        // studio is not found
        Map<String, List<Movie>> moviesByStudio = movieDataSource.getMoviesByStudio();
        return moviesByStudio.getOrDefault(studio, Collections.emptyList());
    }

    /**
     * When the Map does not yet contain a mapping for the given studio, add a
     * mapping for the studio (adding an empty list)
     *
     * @param studio
     *               The Studio to 'add'
     * @return The List of movies for this studio
     */
    public List<Movie> addStudioIfAbsent(String studio) {
        // CODE3:Return a List of movies for this studio
        Map<String, List<Movie>> moviesByStudio = movieDataSource.getMoviesByStudio();
        List<Movie> movies = moviesByStudio.computeIfAbsent(studio, s -> new ArrayList<>());
        return movies;
    }

    /**
     * Get all available movies
     *
     * List is sorted By Studio first, and by title second
     *
     * @return A SORTED list of all movies
     */
    public List<Movie> getAllMovies() {
        // CODE4:Return a custom SORTED list of all movies
        List<Movie> allMovies = movieDataSource.getAllMovies();
        Comparator<Movie> studioComparator = Comparator.comparing(Movie::getStudio);
        Comparator<Movie> titleComparator = Comparator.comparing(Movie::getTitle);
        Comparator<Movie> combined = studioComparator.thenComparing(titleComparator);
        allMovies.sort(combined);
        return allMovies;
    }

    /**
     * Returns a Map where the Key is the studio name and the value is the
     * number of movies released by this studio
     *
     * @return A Map of String/Integer pairs
     */
    public Map<String, Integer> getNumberOfMoviesByStudio() {
        // CODE5:Return a Map where the Key is the studio name and the value is the
        // number of movies released by this studio
        Map<String, Integer> result = new HashMap<>();
        Map<String, List<Movie>> moviesByStudio = movieDataSource.getMoviesByStudio();
        moviesByStudio.forEach((k, v) -> result.put(k, v.size()));
        return result;
    }

}
