package webdbms.service;

import webdbms.DBMS.Dbms;
import webdbms.DBMS.database.Database;
import webdbms.DBMS.database.DatabaseImpl;
import webdbms.DBMS.exception.StorageException;

import java.util.List;

public class DatabaseService {
    private Dbms dbms;
    private List<Database> databases;

    public List<Database> getDatabases() {
        return databases;
    }

    public void setDatabases(List<Database> databases) {
        this.databases = databases;
    }


    public DatabaseService() {
    }

    public void deleteDatabase(int index) throws StorageException {
        databases.get(index).delete();
        databases.remove(index);
    }

    public void deleteDatabase(Database database) throws StorageException {
        int dbIndex = databases.indexOf(database);
        deleteDatabase(dbIndex);
    }

    public void createDatabase(String name) throws StorageException {
        databases.add(dbms.createDatabase(name));
    }

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
        databases.addAll(dbms.getAllDatabases());
    }

    public Database findByName(String name) throws StorageException {
        Database database = dbms.createDatabase(name);
        return databases.get(databases.indexOf(database));
    }

}
