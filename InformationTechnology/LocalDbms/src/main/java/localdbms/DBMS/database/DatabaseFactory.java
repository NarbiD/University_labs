package localdbms.DBMS.database;

import localdbms.DBMS.exception.StorageException;

@FunctionalInterface
public interface DatabaseFactory {
    Database getDatabase() throws StorageException;
}
