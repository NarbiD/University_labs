package localdbms.DBMS;

import localdbms.DBMS.database.Database;
import localdbms.DBMS.exception.StorageException;

import java.util.Collection;
import java.util.Optional;

public interface Dbms {
    void setDatabases(Collection<Database> databases);
    void loadDatabaseFromStorage() throws StorageException;
    Database createDatabase(String name) throws StorageException;
    void deleteDatabase(String name) throws StorageException;
    Optional<Database> getDatabase(String name);
    Collection<Database> getAllDatabases();
}
