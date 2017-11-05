package webdbms.DBMS;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public final static String LOCATION = new File("").getAbsolutePath() + "/src/main/resources/databases/";

    private String name;
    private List<Table> tables;

    public Database() throws Exception {
        this("");
    }

    public Database(String name) throws Exception {
        this.name = name;
        this.tables = new ArrayList<>();
    }

    public void loadTablesFromStorage() throws Exception {
        File[] files = new File(LOCATION + this.name).listFiles();
        for (File entry : files != null ? files : new File[0]) {
            if (entry.isFile()) {
                Table table = new Table(entry.getName(), this.getName());
                table.loadDataFromFile();
                tables.add(table);
            }
        }
    }

    public void setName(String name) throws Exception {
        if (this.name == null || this.name.equals("")) {
            this.name = name;
        } else {
            throw new Exception("You can not rename the database");
        }
    }

    public void addTable(Table table) throws Exception {
        if (tables.contains(table)) {
            throw new Exception("Table with the same name already exists");
        }
        tables.add(table);
    }

    public List<Table> getTables() {
        return tables;
    }

    public String getName() {
        return this.name;
    }

    public void deleteTable(String name) throws Exception {
        Table table = new Table(name, this.getName());
        tables.remove(table);
        table.delete();
    }

    public void save() throws Exception {
        if (name.equals("")) {
            throw new Exception("No name is given for the database");
        }
        File path = new File(LOCATION + this.name);
        if (!Database.doesDatabaseExist(name) && !path.mkdir()) {
            throw new Exception("Can not create database on storage");
        }
        for (Table table : tables) {
            table.writeToFile();
        }
    }

    public void delete() throws Exception {
        if (Database.doesDatabaseExist(this.name, LOCATION)) {
            File path = new File(LOCATION + this.name);
            for (Table table : this.tables) {
                Table.delete(table.getName(), LOCATION + this.name + File.separator);
            }
            if (!path.delete()) {
                throw new Exception("Can not delete database from storage");
            }
        } else {
            throw new Exception("Database does not exist on storage");
        }
    }

    public static boolean doesDatabaseExist(String name) {
        return doesDatabaseExist(name, LOCATION);
    }

    static boolean doesDatabaseExist(String name, String location) {
        File path = new File(location + name);
        return path.isDirectory();
    }
}
