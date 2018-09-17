package io.malevich.server.utils;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.FileSystemResourceAccessor;

import java.sql.Connection;

public class TestUtils {

    public static void initFunction(Connection connection) {
        Database database = null;
        try {
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("src/main/database/app.changelog.xml", new FileSystemResourceAccessor(), database);
            liquibase.update("");

            liquibase = new Liquibase("src/test/database/data.changelog.xml", new FileSystemResourceAccessor(), database);
            liquibase.update("");

        } catch (LiquibaseException e) {
            e.printStackTrace();
        }
    }
}
