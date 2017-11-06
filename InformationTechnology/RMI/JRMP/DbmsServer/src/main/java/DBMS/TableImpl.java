package DBMS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class TableImpl extends UnicastRemoteObject implements Table {

    private IntegerInvlConstraint constraint;
    private String location;
    private String name;
    private List<DataType> types;
    private List<String> columnNames;
    private List<Entry> entries;

    public TableImpl(String name, String dbName) throws Exception {
        this(name, dbName, new ArrayList<>(), new ArrayList<>(), new IntegerInvlConstraint());
    }

    public TableImpl(String name, String dbName, List<DataType> types, List<String> columnNames, IntegerInvlConstraint constraint) throws Exception {
        if (name.equals("")) {
            throw new Exception("Required table name");
        }
        this.name = name;
        this.entries = new ArrayList<>();
        this.types = types;
        this.columnNames = columnNames;
        this.constraint = constraint;
        this.location = DatabaseImpl.LOCATION + dbName + File.separator;
    }

    @SuppressWarnings("unchecked")
    public void loadDataFromFile() throws Exception {
        if (TableImpl.doesTableExist(this.name, this.location)) {
            try {
                JSONObject readData = readJsonFromFile(this.location + this.name);
                List<DataType> readTypes = new ArrayList<>();
                ((JSONArray) readData.get("columnNames")).toList().forEach(o -> columnNames.add(o.toString()));
                ((JSONArray) readData.get("columnTypes")).forEach(type -> readTypes.add(DataType.valueOf(type.toString())));

                if (this.types.equals(readTypes) || types.isEmpty()) {
                    this.types = readTypes;
                    this.entries = getEntriesFromJson((JSONArray) readData.get("entries"), readTypes);
                    for (int i = 0; i < entries.size(); i++) {
                        this.entries.get(i).setFile(((JSONArray) readData.get("files")).get(i).toString());
                    }
                } else {
                    throw new Exception("Expected types " + readTypes + " but " + this.types + " found");
                }
                JSONArray constraints = (JSONArray) readData.get("constraint");
                if (constraints.length() != 0) {
                    this.constraint = new IntegerInvlConstraint(constraints.getInt(0), constraints.getInt(1));
                }
            } catch (ClassCastException | NullPointerException e) {
                throw new Exception("Unsupported file format", e);
            }
        }
    }

    private JSONObject readJsonFromFile(String fileLocation) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileLocation))) {
            return new JSONObject(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException("Can not read table from storage", e);
        }
    }

    private List<Entry> getEntriesFromJson(JSONArray jsonArray, List<DataType> types) throws Exception {
        List<Entry> entries = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject)json;
            EntryImpl entry = new EntryImpl(jsonObject, types, constraint);
            entries.add(entry);
        }
        return entries;
    }

    public void writeToFile() throws Exception {
        if (this.name.equals("") ) {
            throw new Exception("Expected defined name");
        }
        JSONObject outputJson = new JSONObject();
        outputJson.put("columnNames", this.columnNames);
        outputJson.put("columnTypes", this.types);
        outputJson.put("entries", this.getJsonArray());
        JSONArray constraints = new JSONArray();
        if (constraint.isDefined()) {
            constraints.put(this.constraint.getMinValue());
            constraints.put(this.constraint.getMaxValue());
        }
        outputJson.put("constraint", constraints);
        JSONArray files = new JSONArray();
        entries.forEach(entry -> {
            String file = null;
            try {
                file = entry.getFile();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            files.put(file != null ? file : "");
        });
        outputJson.put("files", files);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.location + this.name))) {
            outputJson.write(writer);
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

    public void addRow(List<Object> values, String file) throws Exception {
        if (isValuesValid(values)) {
            EntryImpl entry = new EntryImpl(values, types, constraint);
            entry.setFile(file != null ? file : "");
            entries.add(entry);
        }
    }

    private boolean isValuesValid(List<Object> values) throws Exception {
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
        return true;
    }

    public void setRow(int entryIndex, List<Object> values, String file) throws Exception {
        if (isValuesValid(values)) {
            EntryImpl entry = new EntryImpl(values, types, constraint);
            entry.setFile(file != null ? file : "");
            entries.set(entryIndex, entry);
        }
    }

    public void deleteRow(int rowNumber) {
        entries.remove(rowNumber);
    }

    public void delete() throws Exception {
        TableImpl.delete(name, location);
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

    public void setConstraint(int min, int max) {
        this.constraint = new IntegerInvlConstraint(min, max);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableImpl)) return false;

        TableImpl table = (TableImpl) o;

        return (location != null ? location.equals(table.location) : table.location == null) &&
                (name != null ? name.equals(table.name) : table.name == null);
    }

    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    private static boolean doesTableExist(String name, String location) {
        File table = new File(location + name);
        return table.isFile();
    }

    static void delete(String name, String location) throws Exception {
        if (doesTableExist(name, location)) {
            File table  = new File(location + name);
            if(!table.delete()) {
                throw new Exception("Can not delete table from storage");
            }
        }
    }
}
