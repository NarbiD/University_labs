package localdbms.database;

import localdbms.database.exception.DatabaseException;
import localdbms.database.exception.EntryException;
import localdbms.database.exception.TableException;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Database {
    private final static String REL_DEFAULT_LOCATION = "/src/main/resources/databases/";
    private final static String ABS_DEFAULT_LOCATION = new File("").getAbsolutePath() + REL_DEFAULT_LOCATION;
    private String name;
    private Set<Table> tables;
    private String location;

    public Database(String name) {
        this(name, ABS_DEFAULT_LOCATION);
    }

    public Database(String name, String location) {
        this.name = name;
        this.location = location;
        this.tables = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public static String getDefaultLocation() {
        return ABS_DEFAULT_LOCATION;
    }

    @Override
    public String toString() {
        return "Database " + name;
    }

    public Table createTable(String name, DataType... columnType) {
        String tableLocation = this.location + this.name + File.separator;
        Table table = new Table(name, tableLocation, columnType);
        tables.add(table);
        return table;
    }

    public void deleteTable(String name) throws TableException {
        String tableLocation = this.location + this.name + File.separator;
        Table.delete(name, tableLocation);
    }

    public static boolean isDatabaseExists(String name) {
        return isDatabaseExists(name, ABS_DEFAULT_LOCATION);
    }

    public static boolean isDatabaseExists(String name, String location) {
        File path = new File(location + name);
        return path.isDirectory();
    }

    public boolean isTableExists(String tableName) {
        String tableLocation = this.location + this.name + File.separator;
        return Table.isTableExists(tableName, tableLocation);
    }

    public void save() throws DatabaseException, EntryException {
        File path = new File(this.location + this.name);
        if (!isDatabaseExists(name) && !path.mkdir()) {
            throw new DatabaseException("Can not create database on storage");
        }
        for (Table table : tables) {
            table.writeToFile();
        }
    }

    public void delete() throws DatabaseException {
        if (isDatabaseExists(name, location)) {
            File path = new File(this.location + this.name);
            tables.forEach(table -> {
                try {
                    Table.delete(table.getName(), this.location + this.name + File.separator);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (!path.delete()) {
                throw new DatabaseException("Can not delete database from storage");
            }
        } else {
            throw new DatabaseException("Database does not exist on storage");
        }
    }
}
