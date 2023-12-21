package com.example.javaapplication.movies.repository;

import com.example.javaapplication.movies.domain.Actor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class ActorRepository implements IActorRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Actor> rowMapper;

    public ActorRepository(JdbcTemplate jdbcTemplate, RowMapper<Actor> actorRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = actorRowMapper;
    }

    @Override
    public Actor get(Long actorsId) {

        Actor actor = jdbcTemplate.queryForObject("SELECT name, age, gender, nationality\n" +
                "\tFROM actors Where actors_id = " + actorsId, rowMapper);

        return actor;
    }

    @Override
    public Actor add(Long movieId, Actor actorsToBeAdded) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.actors(\n" +
                    "\tname, age, gender, nationality, movie_table_id)\n" +
                    "\tVALUES (?, ?, ?, ?, ?)", new String[]{"actors_id"});
            preparedStatement.setString(1,actorsToBeAdded.getName());
            preparedStatement.setInt(2,actorsToBeAdded.getAge());
            preparedStatement.setString(3,actorsToBeAdded.getGender());
            preparedStatement.setString(4,actorsToBeAdded.getNationality());
            preparedStatement.setLong(5,movieId);

            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
        Number keyGenerated = generatedKeyHolder.getKey();
        Long key = keyGenerated.longValue();

        return get(key);
    }

    @Override
    public Actor update(Long actorsId, Actor actorsToBeAdded, Long movieId) {

        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.actors\n" +
                    "\tSET name=?, age=?, gender=?, nationality=?, movie_table_id=?\n" +
                    "\tWHERE actors_id = ?");
            preparedStatement.setString(1,actorsToBeAdded.getName());
            preparedStatement.setInt(2,actorsToBeAdded.getAge());
            preparedStatement.setString(3,actorsToBeAdded.getGender());
            preparedStatement.setString(4,actorsToBeAdded.getNationality());
            preparedStatement.setLong(5,movieId);
            preparedStatement.setLong(6,actorsId);

            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator);
        return get(actorsId);
    }

    @Override
    public void delete(Long actorsId) {

        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.actors\n" +
                    "\tWHERE actors_id = ?");
            preparedStatement.setLong(1, actorsId);
            return preparedStatement;
        };

        jdbcTemplate.update(preparedStatementCreator);
    }

    @Override
    public List<Actor> getAll() {

        return jdbcTemplate.query("SELECT * FROM actors", rowMapper);
    }
}
