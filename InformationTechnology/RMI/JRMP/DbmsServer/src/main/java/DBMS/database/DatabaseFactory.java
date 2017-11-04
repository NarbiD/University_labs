package DBMS.database;

import DBMS.exception.StorageException;

@FunctionalInterface
public interface DatabaseFactory {
    Database getDatabase() throws StorageException;
}
