package localdbms.database;

import localdbms.database.exception.StorageException;

@FunctionalInterface
public interface DatabaseFactory {
    Database getDatabase() throws StorageException;
}
