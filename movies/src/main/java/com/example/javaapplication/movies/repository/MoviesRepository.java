package com.example.javaapplication.movies.repository;

import com.example.javaapplication.movies.domain.Movies;
import com.example.javaapplication.movies.util.PostgresUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class MoviesRepository implements IMoviesRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Movies> rowMapper;

    public MoviesRepository(JdbcTemplate jdbcTemplate,
                            RowMapper<Movies> moviesRowMapper
                            ) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = moviesRowMapper;
    }

    @Override
    public Movies get(Long movieId) {
        Movies movie = null;
        try {
            movie = jdbcTemplate.queryForObject("SELECT movie_id, title, year, genre, director, rating, length\n" +
                    "\tFROM movies Where movie_id = " + movieId, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return movie;
    }

    @Override
    public Movies add(Movies moviesTobeAdded) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.movies(\n" +
                    "\ttitle, year, genre, director, rating, length)\n" +
                    "\tVALUES (?, ?, ?, ?, ?, ?)", new String[]{"movie_id"});
            preparedStatement.setString(1,moviesTobeAdded.getTitle());
            preparedStatement.setInt(2,moviesTobeAdded.getYear());
            preparedStatement.setString(3,moviesTobeAdded.getGenre().toString());
            preparedStatement.setString(4,moviesTobeAdded.getDirector());
            preparedStatement.setDouble(5,moviesTobeAdded.getRating());
            preparedStatement.setObject(6, PostgresUtil.toPGInterval(moviesTobeAdded.getLength()));
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        Number keyGenerated = generatedKeyHolder.getKey();
        Long key = keyGenerated.longValue();

        return get(key);
    }

    @Override
    public Movies update(Long movieId, Movies updatedMovies) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.movies\n" +
                    "\tSET title=?, year=?, genre=?, director=?, rating=?, length=?\n" +
                    "\tWHERE movie_id = ?");
            preparedStatement.setString(1,updatedMovies.getTitle());
            preparedStatement.setInt(2,updatedMovies.getYear());
            preparedStatement.setString(3,updatedMovies.getGenre().toString());
            preparedStatement.setString(4,updatedMovies.getDirector());
            preparedStatement.setDouble(5,updatedMovies.getRating());
            preparedStatement.setObject(6,PostgresUtil.toPGInterval(updatedMovies.getLength()));
            preparedStatement.setLong(7, movieId);

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator);
        return get(movieId);
    }

    @Override
    public void delete(Long movieId) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.movies\n" +
                    "\tWHERE movie_id = ?");
            preparedStatement.setLong(1, movieId);
            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Override
    public List<Movies> getAll() {
        return jdbcTemplate.query("SELECT * FROM movies", rowMapper);
    }
}
