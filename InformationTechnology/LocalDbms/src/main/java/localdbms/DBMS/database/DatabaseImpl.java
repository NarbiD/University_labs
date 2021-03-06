package localdbms.DBMS.database;

import localdbms.DBMS.table.TableImpl;
import localdbms.DataType;
import localdbms.DBMS.exception.DatabaseException;
import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.exception.TableException;
import localdbms.DBMS.table.Table;
import localdbms.DBMS.table.TableFactory;
import localdbms.DBMS.table.Tables;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseImpl implements Database {

    private String name;
    private List<Table> tables;
    private String location;
    private TableFactory tableFactory;

    public DatabaseImpl() throws StorageException {
        this("");
    }

    public DatabaseImpl(String name) throws StorageException {
        this(name, Databases.ABS_DEFAULT_LOCATION);
    }

    public DatabaseImpl(String name, String location) throws StorageException {
        this.name = name;
        this.location = location;
        this.tables = new ArrayList<>();
        this.tableFactory = TableImpl::new;
    }

    @Override
    public void loadTablesFromStorage() throws StorageException {
        File[] files = new File(this.location + this.name).listFiles();
        for (File entry : files != null ? files : new File[0]) {
            if (entry.isFile()) {
                Table table = tableFactory.getTable();
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
    public void setName(String name) throws DatabaseException {
        if (this.name == null || this.name.equals("")) {
            this.name = name;
        } else {
            throw new DatabaseException("You can not rename the database");
        }
    }

    @Override
    public List<Table> getTables() {
        return tables;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Table createTable(String name, DataType... columnTypes) throws StorageException {
        Table table = tableFactory.getTable();
        table.setName(name);
        table.setLocation(this.location + this.name + File.separator);
        if (tables.contains(table)) {
            throw new TableException("Table with the same name already exists");
        }
        table.setTypes(columnTypes);
        tables.add(table);
        return table;

    }

    @Override
    public void deleteTable(String name) throws TableException {
        String tableLocation = this.location + this.name + File.separator;
        Tables.delete(name, tableLocation);
    }

    @Override
    public boolean doesTableExist(String tableName) {
        String tableLocation = this.location + this.name + File.separator;
        return Tables.isTableExists(tableName, tableLocation);
    }

    @Override
    public void save() throws StorageException {
        if (name.equals("")) {
            throw new DatabaseException("No name is given for the database");
        }
        File path = new File(this.location + this.name);
        if (!Databases.doesDatabaseExist(name) && !path.mkdir()) {
            throw new DatabaseException("Can not create database on storage");
        }
        for (Table table : tables) {
            table.writeToFile();
        }
    }

    @Override
    public void delete() throws DatabaseException, TableException {
        if (Databases.doesDatabaseExist(this.name, this.location)) {
            File path = new File(this.location + this.name);
            for (Table table : this.tables) {
                Tables.delete(table.getName(), this.location + this.name + File.separator);
            }
            if (!path.delete()) {
                throw new DatabaseException("Can not delete database from storage");
            }
        } else {
            throw new DatabaseException("Database does not exist on storage");
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
