package localdbms.service;

import javafx.collections.ObservableList;
import localdbms.DBMS.Dbms;
import localdbms.DBMS.Database;

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

    public void deleteDatabase(int index) throws Exception {
        databases.get(index).delete();
        databases.remove(index);
    }

    public void createDatabase(String name) throws Exception {
        databases.add(dbms.createDatabase(name));
    }

    public void setDbms(Dbms dbms) {
        this.dbms = dbms;
        databases.addAll(dbms.getAllDatabases());
    }

}
