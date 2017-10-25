package webdbms.facades;

import webdbms.DBMS.Dbms;

import java.util.ArrayList;
import java.util.List;

public class DbmsFacade {

    private Dbms dbms;

    public DbmsFacade(Dbms dbms) {
        this.dbms = dbms;
    }

    public List<String> getDatabaseNames() {
        List<String> names = new ArrayList<>();
        dbms.getAllDatabases().forEach(database -> names.add(database.getName()));
        return names;
    }
}
