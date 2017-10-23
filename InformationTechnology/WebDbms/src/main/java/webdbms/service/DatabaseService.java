package webdbms.service;

import javafx.collections.ObservableList;
import webdbms.DBMS.Dbms;
import webdbms.DBMS.database.Database;
import webdbms.DBMS.exception.StorageException;

public class DatabaseService {
    private Dbms dbms;
    private ObservableList<Database> databases;

    public ObservableList<Database> getDatabases() {
        return databases;
    }

    public void setDatabases(ObservableList<Database> databases) {
        this.databases = databases;
    }


    public DatabaseService() {
    }

    public void deleteDatabase(int index) throws StorageException {
        databases.get(index).delete();
        databases.remove(index);
    }

    public void createDatabase(String name) throws StorageException {
        databases.add(dbms.createDatabase(name));
    }

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
        databases.addAll(dbms.getAllDatabases());
    }

}
