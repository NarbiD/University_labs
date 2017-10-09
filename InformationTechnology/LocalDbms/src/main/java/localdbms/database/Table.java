package localdbms.database;

import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Table {
    private String dbLocation;
    private String name;
    private JSONObject data;


    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    Table(String name, String dbLocation, DataType... columnType) {
        this.dbLocation = dbLocation;
        this.name = name;
        data = new JSONObject();
    }

    public static boolean isTableExists(String name, String dbLocation) {
        File table = new File(dbLocation + "/" + name);
        return table.isFile();
    }

    void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dbLocation + "/" + name))) {
            writer.write(getData().toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delete(String name, String dbLocation) throws Exception {
        if (isTableExists(name, dbLocation)) {
            File table  = new File(dbLocation + "/" + name);
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
