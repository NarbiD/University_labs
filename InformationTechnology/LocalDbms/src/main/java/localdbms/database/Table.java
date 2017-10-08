package localdbms.database;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Table {
    private Database parentDatabase;
    private String name;
    private JSONObject data;


    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Table(String name, Database parentDatabase, DataType... columnType) {
        this.parentDatabase = parentDatabase;
        this.name = name;
        data = new JSONObject();
    }

    public static boolean isTableExists(String name, Database database) {
        String path = new File("").getAbsolutePath();
        File table  =
                new File(path + "/src/main/resources/databases/" + database.getName() + "/" + name);
        return table.isFile();
    }

    void writeToFile() {
        String path = new File("").getAbsolutePath();
        String pathToDatabases = "/src/main/resources/databases/";
        File pathToCurrentDatabase = new File(path + pathToDatabases + parentDatabase.getName());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(pathToCurrentDatabase + "/" + name))) {
            writer.write(getData().toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String name, Database parentDatabase) throws Exception {
        if (isTableExists(name, parentDatabase)) {
            String path = new File("").getAbsolutePath();
            File table  =
                    new File(path + "/src/main/resources/databases/" + parentDatabase.getName() + "/" + name);
            if(!table.delete()) {
                throw new Exception();
            }
        }
    }

    JSONObject getData() {
        data.put("Denis", "Ignashov");
        data.put("Arthur", "King");
        return data;
    }
}
