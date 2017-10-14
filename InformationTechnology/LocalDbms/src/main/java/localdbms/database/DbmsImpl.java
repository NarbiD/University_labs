package localdbms.database;

import localdbms.database.exception.StorageException;

import java.io.File;
import java.util.*;

public class DbmsImpl implements Dbms {

    private Set<Database> databases;
    private DatabaseFactory databaseFactory;

    public DbmsImpl() throws StorageException {
        databases = new HashSet<>();
        databaseFactory = DatabaseImpl::new;
        loadDatabasesFromStorage();
    }

    private void loadDatabasesFromStorage() throws StorageException {
        File[] dirs = new File(Databases.ABS_DEFAULT_LOCATION).listFiles();
        for (File entry : dirs != null ? dirs : new File[0]) {
            if (entry.isDirectory()) {
                Database database = databaseFactory.getDatabase();
                database.setName(entry.getName());
                databases.add(database);
            }
        }
    }

    @Override
    public void createDatabase(String name) throws StorageException {
        Database database = databaseFactory.getDatabase();
        database.setName(name);
        database.save();
        databases.add(database);
    }

    @Override
    public void deleteDatabase(String name) throws StorageException {
        Database database = databaseFactory.getDatabase();
        database.setName(name);
        database.delete();
        databases.remove(database);
    }

    @Override
    public Optional<Database> getDatabase(String name) {
        for (Database db : databases) {
            if (name.equals(db.getName())){
                return Optional.of(db);
            }
        }
        return Optional.empty();
    }

    @Override
    public Set<Database> getAllDatabases() {
        return databases;
    }
}
