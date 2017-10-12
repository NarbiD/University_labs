package localdbms.database;

@FunctionalInterface
public interface DatabaseFactory {
    Database getDatabase();
}
