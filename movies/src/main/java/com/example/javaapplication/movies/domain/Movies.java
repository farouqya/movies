package com.example.javaapplication.movies.domain;

import org.postgresql.util.PGInterval;

import java.time.Duration;
import java.util.List;

public class Movies {

    private Long id;
    private String title;
    private int year;
    private Genre genre;
    private List<Actor> actors;
    private String director;
    private double rating;
    private Duration length;
    private String genreString;

    public Movies(String title, int year, String genreString, List<Actor> actors, String director, double rating, Duration length) {
        this.title = title;
        this.year = year;
        try {
            this.genre = Genre.valueOf(Genre.class, genreString.toUpperCase());
        } catch (IllegalArgumentException e) {

            System.err.println("Invalid Movies Genre. Please pick one of these: " +
                    "ACTION, COMEDY, DRAMA, HORROR, ROMANCE, SCIFIC, THRILLER, OTHER");
        }
        this.actors = actors;
        this.director = director;
        this.rating = rating;
        this.length = length;
    }

    public Movies() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Genre getGenre() {
       return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Duration getLength() {
        return length;
    }

    public void setLength(PGInterval pgInterval) {
        if(pgInterval != null) {
            long days = pgInterval.getDays();
            long hours = pgInterval.getHours();
            long minutes = pgInterval.getMinutes();
            double seconds = pgInterval.getSeconds();

            this.length = Duration.ofDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds((long) seconds);
        }
    }
  
    public String getGenreString() {
        return genreString;
    }

    public void setGenreString(String genreString) {
        this.genreString = genreString;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", genre=" + genre +
                ", actors=" + actors +
                ", director='" + director + '\'' +
                ", rating=" + rating +
                ", length=" + length +
                '}';
    }
}