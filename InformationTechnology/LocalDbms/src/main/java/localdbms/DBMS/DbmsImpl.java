package localdbms.DBMS;

import localdbms.DBMS.database.Database;
import localdbms.DBMS.database.DatabaseFactory;
import localdbms.DBMS.database.DatabaseImpl;
import localdbms.DBMS.database.Databases;
import localdbms.DBMS.exception.StorageException;

import java.io.File;
import java.util.*;

public class DbmsImpl implements Dbms {

    @Override
    public void setDatabases(Collection<Database> databases) {
        this.databases = databases;
    }

    private Collection<Database> databases;
    private DatabaseFactory databaseFactory;

    public DbmsImpl() throws StorageException {
        databaseFactory = DatabaseImpl::new;
    }

    public void loadDatabaseFromStorage() throws StorageException {
        File[] dirs = new File(Databases.ABS_DEFAULT_LOCATION).listFiles();
        for (File entry : dirs != null ? dirs : new File[0]) {
            if (entry.isDirectory()) {
                Database database = databaseFactory.getDatabase();
                database.setName(entry.getName());
                database.loadTablesFromStorage();
                databases.add(database);
            }
        }
    }

    @Override
    public Database createDatabase(String name) throws StorageException {
        Database database = databaseFactory.getDatabase();
        database.setName(name);
        database.save();
        databases.add(database);
        return database;
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
    public Collection<Database> getAllDatabases() {
        return databases;
    }
}
