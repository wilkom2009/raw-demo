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

		Stream<Movie> data = List
				.of(createObject(
						"111792;Nutty Professor II: The Klumps (HD-DVD);Universal;19.98;PG-13;Comedy;07-24-2007"))
				.stream();

		if (movieData != null)
			return movieData.stream();

		return data;
		// try (Stream<String> stream = new BufferedReader(
		// new
		// InputStreamReader(MovieDataSource.class.getResourceAsStream("movies.dat"))).lines();)
		// {
		// movieData = stream.map(line ->
		// createObject(line)).collect(Collectors.toList());
		// }

		// return movieData.stream();
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
		// List<Movie> movieData = new ArrayList<>();
		// try (Stream<String> stream = new BufferedReader(
		// new
		// InputStreamReader(getClass().getResourceAsStream("movies.dat"))).lines();) {
		// stream.forEach(line -> movieData.add(createObject(line)));
		// }

		List<Movie> movieData = List.of(
				createObject("111792;Nutty Professor II: The Klumps (HD-DVD);Universal;19.98;PG-13;Comedy;07-24-2007"),
				createObject(
						"246036;Patrick Swayze Collection (Blu-Ray): Ghost (1990) / Point Break / Next Of Kin;Warner Brothers;19.98;VAR;VAR;06-04-2013"),
				createObject(
						"16588;Friends: The Complete 1st Season (Old Version);Warner Brothers;19.98;NR;TV Classics;04-30-2002"),
				createObject(
						"149462;Young Sherlock Holmes (DVD/CD Combo/ Checkpoint);Paramount;14.99;PG-13;Mystery/Suspense;02-03-2009"),
				createObject(
						"233671;Town (Blu-ray w/ Digital Copy);Warner Brothers;19.98;R;Action/Adventure;10-09-2012"),
				createObject("112097;Shooter (Paramount/ Pan & Scan);Paramount;12.99;R;Action/Adventure;06-26-2007"),
				createObject("203017;Futurama: Complete Collection (Bender Head);Fox;199.98;NR;Animation;07-06-2010"),
				createObject("195747;Rain Man (Blu-ray);MGM/UA;19.99;R;Drama;02-15-2011"),
				createObject("219047;There Be Dragons (Blu-ray);Fox;14.99;PG-13;War;01-10-2012")
		);

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
