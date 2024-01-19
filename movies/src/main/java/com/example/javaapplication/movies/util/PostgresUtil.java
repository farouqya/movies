package com.example.javaapplication.movies.util;

import org.postgresql.util.PGInterval;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;
import java.time.Duration;

@Configuration
public class PostgresUtil {

    public static PGInterval toPGInterval(Duration duration) {
        long seconds = duration.getSeconds();
        int days = (int) seconds / (24 * 3600);
        int hours = (int)((seconds % (24 * 3600)) / 3600);
        int minutes = (int) ((seconds % 3600) / 60);
        double secondsFraction = seconds % 60 + duration.getNano() / 1000000000.0;

        return new PGInterval(0,0,days,hours, minutes, secondsFraction);
    }

    public static PGInterval toPGInterval(String durationString) throws SQLException {
        PGInterval pgInterval = new PGInterval(durationString);

        long days = pgInterval.getDays();
        long hours = pgInterval.getHours();
        long minutes = pgInterval.getMinutes();
        double seconds = pgInterval.getSeconds();

        return pgInterval;
    }
}
