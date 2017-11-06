package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import DBMS.Dbms;
import DBMS.Database;

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
        databases = FXCollections.observableArrayList();
    }

    public void deleteDatabase(int index) throws Exception {
        databases.get(index).delete();
        databases.remove(index);
    }

    public void createDatabase(String name) throws Exception {
        databases.add(dbms.createDatabase(name));
    }

    public void setDbms(Dbms dbms){
        this.dbms = dbms;
        try {
            databases.addAll(this.dbms.getAllDatabases());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
