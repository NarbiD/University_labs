package localdbms.database;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class Database {
    private String name;
    private Set<Table> tables;

    public Database(String name) {
        this.name = name;
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
        Table table = new Table(name, this, columnType);
        tables.add(table);
    }

    public void deleteTable(String name) throws Exception {
        Table.delete(name, this);
    }

    public static boolean isDatabaseExists(String name) {
        String path = new File("").getAbsolutePath();
        File databasePath = new File(path + "/src/main/resources/databases/" + name);
        return databasePath.isDirectory();
    }

    public boolean isTableExists(String name) {
        return Table.isTableExists(name, this);
    }

    public void save() {
        String path = new File("").getAbsolutePath();
        File dbPath = new File(path + "/src/main/resources/databases/" + name);
        if (!isDatabaseExists(name)) {
            if (dbPath.mkdir()) {
                System.out.println("Database " + name + " was created.");
            } else {
                throw new RuntimeException();
            }
        }
        tables.forEach(Table::writeToFile);

    }

    public void delete() throws Exception {
        String path = new File("").getAbsolutePath();
        File dbPath = new File(path + "/src/main/resources/databases/" + name);
        if (isDatabaseExists(name)) {
            tables.forEach(table -> {
                try {
                    Table.delete(table.getName(), this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            dbPath.delete();
        } else {
            throw new Exception();
        }

    }
}
