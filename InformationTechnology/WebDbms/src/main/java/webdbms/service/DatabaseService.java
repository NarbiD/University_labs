package webdbms.service;

import webdbms.DBMS.Dbms;
import webdbms.DBMS.Database;

import webdbms.service.exception.DatabaseNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseService {
    private Dbms dbms;

    public DatabaseService() {
    }

    public void deleteDatabase(String name) throws Exception {
        dbms.deleteDatabase(name);
    }

    public void createDatabase(String name) throws Exception {
        dbms.createDatabase(name);
    }

    public Collection<String> getDatabaseNames() {
        List<String> databaseNames = new ArrayList<>();
        dbms.getAllDatabases().forEach(database -> databaseNames.add(database.getName()));
        return databaseNames;
    }

    public Database findByName(String name) throws Exception {
        return dbms.getAllDatabases().stream()
                .filter(db -> name.equals(db.getName())).findAny()
                .orElseThrow(() -> new DatabaseNotFoundException(name));
    }

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
    }
}
