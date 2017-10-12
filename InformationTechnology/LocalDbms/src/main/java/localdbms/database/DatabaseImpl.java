package localdbms.database;

import localdbms.database.exception.DatabaseException;
import localdbms.database.exception.EntryException;
import localdbms.database.exception.StorageException;
import localdbms.database.exception.TableException;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class DatabaseImpl implements Database {

    private String name;
    private Set<Table> tables;
    private String location;
    TableFactory tableFactory = TableImpl::new;

    public DatabaseImpl() {
        this("");
    }

    public DatabaseImpl(String name) {
        this(name, Databases.ABS_DEFAULT_LOCATION);
    }

    public DatabaseImpl(String name, String location) {
        this.name = name;
        this.location = location;
        this.tables = new HashSet<>();
    }

    public void setName(String name) throws DatabaseException {
        if (this.name == null || this.name.equals("")) {
            this.name = name;
        } else {
            throw new DatabaseException("You can not rename the database");
        }
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Database " + name;
    }

    public Table createTable(String name, DataType... columnTypes) throws EntryException {
        Table table = tableFactory.getTable();
        table.setName(name);
        table.setLocation(this.location + this.name + File.separator);
        table.setTypes(columnTypes);
        tables.add(table);
        return table;
    }

    public void deleteTable(String name) throws TableException {
        String tableLocation = this.location + this.name + File.separator;
        Tables.delete(name, tableLocation);
    }

    public boolean doesTableExist(String tableName) {
        String tableLocation = this.location + this.name + File.separator;
        return Tables.isTableExists(tableName, tableLocation);
    }

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

    public void delete() throws DatabaseException, TableException {
        if (Databases.doesDatabaseExist(name, location)) {
            File path = new File(this.location + this.name);
            for (Table table : tables) {
                Tables.delete(table.getName(), this.location + this.name + File.separator);
            }
            if (!path.delete()) {
                throw new DatabaseException("Can not delete database from storage");
            }
        } else {
            throw new DatabaseException("Database does not exist on storage");
        }
    }
}
