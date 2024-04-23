package com.example.javaapplication.movies.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.postgresql.util.PGInterval;

import java.sql.SQLException;
import java.time.Duration;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, String> {

    public PGInterval durationToPGInterval(Duration duration) {

        if (duration != null) {
                long seconds = duration.getSeconds();
                int days = (int) seconds / (24 * 3600);
                int hours = (int) ((seconds % (24 * 3600)) / 3600);
                int minutes = (int) ((seconds % 3600) / 60);
                double secondsFraction = seconds % 60 + duration.getNano() / 1000000000.0;

                PGInterval pgInterval = new PGInterval(0, 0, days, hours, minutes, secondsFraction);
                System.out.println("Converted Duration to PGInterval " + pgInterval);
                return pgInterval;

            }
            else {
                return null;
            }
    }

    public Duration PGIntervalToDuration(PGInterval pgInterval) {

        if (pgInterval != null) {
                long days = pgInterval.getDays();
                long hours = pgInterval.getHours();
                long minutes = pgInterval.getMinutes();
                double seconds = pgInterval.getSeconds();

                Duration duration = Duration.ofDays(days).plusHours(hours).plusMinutes(minutes).plusSeconds((long) seconds);
                System.out.println("Converted PGInterval to Duration: " + duration);
                return duration;
            } else {
                return null;
            }
    }

    @Override
    public String convertToDatabaseColumn(Duration duration) {

        PGInterval pgInterval = durationToPGInterval(duration);
        return pgInterval.getValue();
    }

    @Override
    public Duration convertToEntityAttribute(String pgIntervalString) {

        try {
            PGInterval pgInterval = new PGInterval(pgIntervalString);
            return PGIntervalToDuration(pgInterval);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
