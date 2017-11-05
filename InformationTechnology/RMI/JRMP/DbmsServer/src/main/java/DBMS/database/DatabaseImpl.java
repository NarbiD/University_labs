package DBMS.database;

import DBMS.table.TableImpl;
import common.DataType;
import DBMS.table.Table;
import DBMS.table.Tables;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DatabaseImpl extends UnicastRemoteObject implements Database {

    private String name;
    private List<Table> tables;
    private String location;

    public DatabaseImpl() throws IOException {
        this("");
    }

    public DatabaseImpl(String name) throws IOException {
        this(name, Databases.ABS_DEFAULT_LOCATION);
    }

    public DatabaseImpl(String name, String location) throws IOException {
        this.name = name;
        this.location = location;
        this.tables = new ArrayList<>();
    }

    @Override
    public void loadTablesFromStorage() throws IOException {
        File[] files = new File(this.location + this.name).listFiles();
        for (File entry : files != null ? files : new File[0]) {
            if (entry.isFile()) {
                Table table = new TableImpl();
                table.setName(entry.getName());
                table.setLocation(this.location + this.name + File.separator);
                try {
                    table.loadDataFromFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tables.add(table);
            }
        }
    }

    @Override
    public void setName(String name) throws IOException {
        if (this.name == null || this.name.equals("")) {
            this.name = name;
        } else {
            throw new IOException("You can not rename the database");
        }
    }

    @Override
    public List<Table> getTables()throws RemoteException {
        return tables;
    }

    @Override
    public String getName()throws RemoteException {
        return this.name;
    }

    @Override
    public Table createTable(String name, DataType... columnTypes) throws IOException {
        Table table = new TableImpl();
        table.setName(name);
        table.setLocation(this.location + this.name + File.separator);
        if (tables.contains(table)) {
            throw new IOException("Table with the same name already exists");
        }
        table.setTypes(columnTypes);
        tables.add(table);
        return table;

    }

    @Override
    public void deleteTable(String name) throws IOException {
        String tableLocation = this.location + this.name + File.separator;
        Tables.delete(name, tableLocation);
    }

    @Override
    public boolean doesTableExist(String tableName)throws RemoteException {
        String tableLocation = this.location + this.name + File.separator;
        return Tables.isTableExists(tableName, tableLocation);
    }

    @Override
    public void save() throws IOException {
        if (name.equals("")) {
            throw new IOException("No name is given for the database");
        }
        File path = new File(this.location + this.name);
        if (!Databases.doesDatabaseExist(name) && !path.mkdir()) {
            throw new IOException("Can not create database on storage");
        }
        for (Table table : tables) {
            table.writeToFile();
        }
    }

    @Override
    public void delete() throws IOException {
        if (Databases.doesDatabaseExist(this.name, this.location)) {
            File path = new File(this.location + this.name);
            for (Table table : this.tables) {
                Tables.delete(table.getName(), this.location + this.name + File.separator);
            }
            if (!path.delete()) {
                throw new IOException("Can not delete database from storage");
            }
        } else {
            throw new IOException("Database does not exist on storage");
        }
    }

    @Override
    public String toString() {
        return "Database " + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseImpl)) return false;

        DatabaseImpl database = (DatabaseImpl) o;

        boolean isNamesEquals = name != null ? name.equals(database.name) : database.name == null;
        boolean isLocationsEquals = location != null ? location.equals(database.location) : database.location == null;
        return isNamesEquals && isLocationsEquals;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
