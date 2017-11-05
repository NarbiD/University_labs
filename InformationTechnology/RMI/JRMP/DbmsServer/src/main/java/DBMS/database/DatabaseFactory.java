package DBMS.database;

import java.io.IOException;

@FunctionalInterface
public interface DatabaseFactory {
    Database getDatabase() throws IOException;
}
