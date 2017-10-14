package localdbms.database;

import localdbms.database.exception.StorageException;

import java.util.Optional;
import java.util.Set;

public interface Dbms {
    void createDatabase(String name) throws StorageException;
    void deleteDatabase(String name) throws StorageException;
    Optional<Database> getDatabase(String name);
    Set<Database> getAllDatabases();
}
