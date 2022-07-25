package com.wilkom.rawdemo.movies.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Integer id;

    private String title;
    private String studio;

    private Double price;
    private String rating;
    private String genre;
    private LocalDate releaseDate;
}
