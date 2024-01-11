package com.example.javaapplication.movies.config;

import com.example.javaapplication.movies.domain.DatabaseType;
import com.example.javaapplication.movies.exceptions.UnsupportedDatabaseException;
import org.springframework.stereotype.Component;


@Component
public class DatabaseToSelect {


    public static DatabaseType databaseToCompare(String value) throws IllegalArgumentException {

        for (DatabaseType databaseType : DatabaseType.values()) {
            if (databaseType.name().equalsIgnoreCase(value)) {
                switch (databaseType) {
                    case POSTGRES:
                        return DatabaseType.POSTGRES;
                    default:
                        throw new UnsupportedDatabaseException("Unsupported database: " + databaseType);
                }
            }
        }
        throw new UnsupportedDatabaseException("ONLY POSTGRES DATABASE IS SUPPORTED!! ");
    }
}


