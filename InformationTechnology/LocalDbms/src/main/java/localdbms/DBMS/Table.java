package localdbms.DBMS;

import localdbms.DataType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private String location;
    private String name;
    private List<DataType> types;
    private List<String> columnNames;
    private List<Entry> entries;

    public Table(String name, String dbName) throws Exception {
        this(name, dbName, new ArrayList<>(), new ArrayList<>());
    }

    public Table(String name, String dbName, List<DataType> types, List<String> columnNames) throws Exception {
        if (name.equals("")) {
            throw new Exception("Required table name");
        }
        this.name = name;
        this.entries = new ArrayList<>();
        this.types = types;
        this.columnNames = columnNames;
        location = Database.LOCATION + dbName + File.separator;
    }

    public void loadDataFromFile() throws Exception {
        String rowInFile = new JSONArray().toString();
        String readData = rowInFile + '\n' + rowInFile + '\n' + rowInFile;
        if (Table.doesTableExist(this.name, this.location)) {
            readData = readTableFromStorage(this.location + this.name);
        }
        String[] s = readData.split("\n");
        List<DataType> readTypes = new ArrayList<>();
        for (Object title : new JSONArray(s[0]).toList()) {
            columnNames.add(title.toString());
        }
        new JSONArray(s[1]).toList().forEach(type -> readTypes.add(DataType.valueOf(type.toString())));

        if (this.types.equals(readTypes) || types.isEmpty()) {
            this.types = readTypes;
            this.entries = getEntriesFromJson(new JSONArray(s[2]), readTypes);
        } else {
            throw new Exception("Expected types " + readTypes + " but " + this.types + " found");
        }
    }

    private String readTableFromStorage(String storageLocation) {
        try (BufferedReader reader = new BufferedReader(new FileReader(storageLocation))) {
            return reader.readLine() + '\n' + reader.readLine() + '\n' + reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can not read table from storage", e);
        }
    }

    private List<Entry> getEntriesFromJson(JSONArray jsonArray, List<DataType> types) throws Exception {
        List<Entry> entries = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject)json;
            Entry entry = new Entry(jsonObject, types);
            entries.add(entry);
        }
        return entries;
    }

    public void writeToFile() throws Exception {
        if (this.name.equals("") || this.location.equals("") ) {
            throw new Exception("Expected defined name, location and types");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.location + this.name))) {
            writer.write(new JSONArray(this.columnNames).toString() + "\n");
            writer.write(new JSONArray(this.types).toString() + "\n");
            writer.write(this.getJsonArray().toString() + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Can not write table to storage", e);
        }
    }

    private JSONArray getJsonArray() throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Entry entry : this.entries) {
            jsonArray.put(entry.getJson());
        }
        return jsonArray;
    }

    public void addRow(List<Object> values) throws Exception {
        if (values.size() != types.size()) {
            throw new Exception("Expected " + types.size() + " values but " + values.size() + " found");
        }
        for (int i = 0; i < values.size(); i++) {
            if(values.get(i) instanceof String) {
                try {
                    values.set(i, TypeManager.parseObjectByType(values.get(i).toString(), types.get(i)));
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid value " + values.get(i));
                }
            }
        }
        Entry entry = new Entry(values, types);
        entries.add(entry);
    }

    public void deleteRow(int rowNumber) {
        entries.remove(rowNumber);
    }

    public void delete() throws Exception {
        Table.delete(name, location);
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public String getName() {
        return this.name;
    }

    public List<DataType> getTypes() {
        return this.types;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Table)) return false;

        Table table = (Table) o;

        return (location != null ? location.equals(table.location) : table.location == null) &&
                (name != null ? name.equals(table.name) : table.name == null);
    }

    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    static boolean doesTableExist(String name, String location) {
        File table = new File(location + name);
        return table.isFile();
    }

    public static void delete(String name, String location) throws Exception {
        if (doesTableExist(name, location)) {
            File table  = new File(location + name);
            if(!table.delete()) {
                throw new Exception("Can not delete table from storage");
            }
        }
    }
}
