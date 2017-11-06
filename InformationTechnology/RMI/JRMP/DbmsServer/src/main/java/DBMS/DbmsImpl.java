package DBMS;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbmsImpl extends UnicastRemoteObject implements Dbms {

    private List<Database> databases;

    public DbmsImpl() throws Exception {
        databases = new ArrayList<>();
        loadDatabaseFromStorage();
    }

    public void setDatabases(List<Database> databases) {
        this.databases = databases;
    }

    public void loadDatabaseFromStorage() throws Exception {
        File[] dirs = new File(DatabaseImpl.LOCATION).listFiles();
        for (File entry : dirs != null ? dirs : new File[0]) {
            if (entry.isDirectory()) {
                Database database = new DatabaseImpl(entry.getName());
                database.loadTablesFromStorage();
                databases.add(database);
            }
        }
    }

    public Database createDatabase(String name) throws Exception {
        Database database = new DatabaseImpl(name);
        if (databases.contains(database)) {
            throw new Exception("Database with the same name already exists");
        }
        database.save();
        databases.add(database);
        return database;
    }

    public void deleteDatabase(String name) throws Exception {
        Database db = databases.stream().filter(database -> {
            try {
                return name.equals(database.getName());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }).findAny()
            .orElseThrow(() -> new Exception("Database does not exist"));
        db.delete();
        databases.remove(db);
    }

    public Optional<Database> getDatabase(String name) throws RemoteException {
        for (Database db : databases) {
            if (name.equals(db.getName())){
                return Optional.of(db);
            }
        }
        return Optional.empty();
    }

    public List<Database> getAllDatabases() {
        return databases;
    }
}
