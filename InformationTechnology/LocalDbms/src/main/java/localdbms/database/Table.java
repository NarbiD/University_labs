package localdbms.database;

import org.json.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private String dbLocation;
    private String name;

    public List<DataType> getTypes() {
        return types;
    }

    private List<DataType> types;
    private JSONArray data;
    private List<Entry> entries;

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public Table(String name, String dbLocation, DataType... columnType) {
        this.dbLocation = dbLocation;
        String fileLocation = dbLocation + "/" + name;
        this.name = name;
        this.types = Arrays.asList(columnType);
        if (new File(fileLocation).isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
                data = new JSONArray(reader.readLine());
                entries = getEntries(types);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            data = new JSONArray();
            entries = getEntries(types);
        }
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

    public List<Entry> getEntries(List<DataType> types){
        List<Entry> entries = new ArrayList<>();
        for (Object json : data) {
            JSONObject h = (JSONObject)json;
            entries.add(new Entry(types, Arrays.asList(h.toMap().values().toArray())));
        }
        return entries;
    }

    public void addRow(Entry row) {
        data.put(row.getJson());
        entries = getEntries(types);
    }

    public void sort(int fieldNumber) {
        entries.sort(new EntryComparator(fieldNumber));
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public JSONArray getData() {
        return data;
    }
}
