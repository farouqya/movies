package com.example.javaapplication.movies.domain;

import java.time.Duration;
import java.util.List;

public class Movies {

    private String title;
    private int year;
    private Genre genre;
    private List<Actor> actors;
    private String director;
    private double rating;
    private Duration length;

    public Movies(String title, int year, Genre genre, List<Actor> actors, String director, double rating, Duration length) {
        this.title = title;
        this.year = year;
        this.genre = genre;
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

    public void setLength(Duration length) {
        this.length = length;
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
