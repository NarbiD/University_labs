package DBMS;

import DBMS.database.Database;
import DBMS.database.DatabaseFactory;
import DBMS.database.DatabaseImpl;
import DBMS.database.Databases;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class DbmsImpl extends UnicastRemoteObject implements Dbms {

    private Collection<Database> databases;
    private DatabaseFactory databaseFactory;

    public DbmsImpl() throws IOException {
        super();
        databases = new ArrayList<>();
        databaseFactory = (DatabaseFactory & Serializable)DatabaseImpl::new;
        loadDatabaseFromStorage();
    }

    @Override
    public void setDatabases(Collection<Database> databases) throws RemoteException {
        this.databases = databases;
    }

    public void loadDatabaseFromStorage() throws IOException {
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
    public Database createDatabase(String name) throws IOException{
        Database database = databaseFactory.getDatabase();
        database.setName(name);
        if (databases.contains(database)) {
            throw new IOException("Database with the same name already exists");
        }
        database.save();
        databases.add(database);
        return database;
    }

    @Override
    public void deleteDatabase(String name) throws IOException {
        Database database = databaseFactory.getDatabase();
        database.setName(name);
        database.delete();
        databases.remove(database);
    }

    @Override
    public Optional<Database> getDatabase(String name) throws RemoteException {
        for (Database db : databases) {
            if (name.equals(db.getName())){
                return Optional.of(db);
            }
        }
        return Optional.empty();
    }

    @Override
    public Collection<Database> getAllDatabases() throws RemoteException {
        return databases;
    }
}
