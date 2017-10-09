package localdbms.database;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Database {
    private final static String DEFAULT_LOCATION = new File("").getAbsolutePath() + "/src/main/resources/databases/";
    private String name;
    private Set<Table> tables;

    public String getLocation() {
        return location;
    }

    private String location;

    public Database(String name) {
        this(name, DEFAULT_LOCATION);
    }

    public Database(String name, String location) {
        this.name = name;
        this.location = location;
        this.tables = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Database " + name;
    }

    public void createTable(String name, DataType... columnType) {
        Table table = new Table(name, this.location + "/" + this.name + "/", columnType);
        tables.add(table);
    }

    public void deleteTable(String name) throws Exception {
        Table.delete(name, this.location + "/" + this.name + "/");
    }

    public static boolean isDatabaseExists(String name) {
        return isDatabaseExists(name, DEFAULT_LOCATION);
    }

    public static boolean isDatabaseExists(String name, String location) {
        File path = new File(location + name);
        return path.isDirectory();
    }

    public boolean isTableExists(String tableName) {
        return Table.isTableExists(tableName, this.location  + "/" + this.name);
    }

    public void save() {
        File path = new File(this.location  + "/" + this.name);
        if (!isDatabaseExists(name)) {
            if (path.mkdir()) {
                System.out.println("Database " + name + " was created.");
            } else {
                throw new RuntimeException();
            }
        }
        tables.forEach(Table::writeToFile);

    }

    public void delete() throws Exception {
        if (isDatabaseExists(name)) {
            File path = new File(this.location  + "/" + this.name);
            tables.forEach(table -> {
                try {
                    Table.delete(table.getName(), this.location  + "/" + this.name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            if (!path.delete()){
                throw new Exception();
            }
        } else {
            throw new Exception();
        }

    }
}
