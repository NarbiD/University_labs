package localdbms.database;

import localdbms.database.exception.EntryException;
import localdbms.database.exception.TableException;
import org.json.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Table {
    private String location;
    private String name;
    private List<DataType> types;
    private List<Entry> entries;

    public List<DataType> getTypes() {
        return this.types;
    }

    String getName() {
        return this.name;
    }

    public Table(String name, String location, DataType... columnType) {
        this.location = location;
        this.name = name;
        this.types = Arrays.asList(columnType);
        String readData = new JSONArray().toString();
        if (isTableExists(name, location)) {
            readData = readTableFromStorage(location + name);
        }
        this.entries = getEntries(this.types, new JSONArray(readData));
    }

    private String readTableFromStorage(String storageLocation){
        try (BufferedReader reader = new BufferedReader(new FileReader(storageLocation))) {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can not read table from storage", e);
        }
    }

    public static boolean isTableExists(String name, String location) {
        File table = new File(location + name);
        return table.isFile();
    }

    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    void writeToFile() throws EntryException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.location + this.name))) {
            writer.write(this.getJsonArray().toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Can not write table to storage", e);
        }
    }
    private JSONArray getJsonArray() throws EntryException {
        JSONArray jsonArray = new JSONArray();
        for (Entry entry : this.entries) {
            jsonArray.put(entry.getJson());
        }
        return jsonArray;
    }

    public static void delete(String name, String location) throws TableException {
        if (isTableExists(name, location)) {
            File table  = new File(location + name);
            if(!table.delete()) {
                throw new TableException("Can not delete table from storage");
            }
        }
    }

    public List<Entry> getEntries(List<DataType> types, JSONArray jsonArray){
        List<Entry> entries = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject h = (JSONObject)json;
            entries.add(new Entry(types, Arrays.asList(h.toMap().values().toArray())));
        }
        return entries;
    }

    public void addRows(Entry... rows) {
        entries.addAll(Arrays.asList(rows));
    }

    public void sort(int fieldNumber) {
        entries.sort(new EntryComparator(fieldNumber));
    }

    public List<Entry> getEntries() {
        return entries;
    }
}
