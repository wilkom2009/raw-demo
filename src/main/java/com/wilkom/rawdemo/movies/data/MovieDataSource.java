package com.wilkom.rawdemo.movies.data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.wilkom.rawdemo.movies.model.Movie;

public class MovieDataSource {

	private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM-dd-yyyy");

	private static MovieDataSource currentInstance;

	private Map<String, List<Movie>> moviesByStudio;

	private static List<Movie> movieData;

	public static Stream<Movie> getAllStreamMovies() {

		if (movieData != null)
			return movieData.stream();

		try (Stream<String> stream = new BufferedReader(
				new InputStreamReader(MovieDataSource.class.getResourceAsStream("movies.dat"))).lines();) {
			movieData = stream.map(line -> createObject(line)).collect(Collectors.toList());
		}

		return movieData.stream();
	}

	public static MovieDataSource getInstance() {
		if (currentInstance == null) {
			currentInstance = new MovieDataSource();
			currentInstance.readDataFiles();
		}
		return currentInstance;
	}

	public List<Movie> getAllMovies() {
		return moviesByStudio.values().stream().flatMap(lst -> lst.stream()).collect(Collectors.toList());
	}

	public Map<String, List<Movie>> getMoviesByStudio() {
		return moviesByStudio;
	}

	public Set<String> getStudios() {
		return moviesByStudio.keySet();
	}

	private void readDataFiles() {
		List<Movie> movieData = new ArrayList<>();
		try (Stream<String> stream = new BufferedReader(
				new InputStreamReader(getClass().getResourceAsStream("movies.dat"))).lines();) {
			stream.forEach(line -> movieData.add(createObject(line)));
		}

		moviesByStudio = movieData.stream().collect(Collectors.groupingBy(Movie::getStudio));
	}

	private static Movie createObject(String data) {
		String[] fields = data.split(";");
		// System.out.println(fields[1]);

		Movie movie = new Movie();
		movie.setId(Integer.parseInt(fields[0]));
		movie.setTitle(fields[1]);
		movie.setStudio(fields[2]);
		movie.setPrice(Double.parseDouble(fields[3]));
		movie.setRating(fields[4]);
		movie.setGenre(fields[5]);

		try {
			movie.setReleaseDate(LocalDate.parse(fields[6], DATE_TIME_FORMATTER));
		} catch (DateTimeParseException e) {
			// ignore
		}

		return movie;

	}

}
