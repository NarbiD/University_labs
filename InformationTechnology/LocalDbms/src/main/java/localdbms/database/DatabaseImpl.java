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
    private TableFactory tableFactory = TableImpl::new;

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

    @Override
    public void setName(String name) throws DatabaseException {
        if (this.name == null || this.name.equals("")) {
            this.name = name;
        } else {
            throw new DatabaseException("You can not rename the database");
        }
    }

    @Override
    public Set<Table> getTables() {
        return tables;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLocation() {
        return this.location;
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

    @Override
    public Table createTable(String name, DataType... columnTypes) throws EntryException {
        Table table = tableFactory.getTable();
        table.setName(name);
        table.setLocation(this.location + this.name + File.separator);
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
}
