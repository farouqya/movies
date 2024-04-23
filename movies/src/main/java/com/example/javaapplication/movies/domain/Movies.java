package com.example.javaapplication.movies.domain;

import com.example.javaapplication.movies.config.DurationConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;
import org.postgresql.util.PGInterval;

import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movies extends AbstractAuditable {

    @Id
    @GeneratedValue(generator = "movies_generator")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "movies_generator", sequenceName = "movies_movie_id_seq", allocationSize = 1)
    private Long movieId;
    private String title;
    private int year;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private String director;
    private double rating;

    @Column(name = "length", columnDefinition = "interval")
    @Convert(converter =  DurationConverter.class)
    @ColumnTransformer(write = "?::interval")
    private Duration length;

    @Transient
    private List<Actor> actors;

    //private String genreString;

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

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
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
  
//    public String getGenreString() {
//        return genreString;
//    }
//
//    public void setGenreString(String genreString) {
//        this.genreString = genreString;
//    }

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