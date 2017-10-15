package localdbms.database;

import localdbms.database.exception.EntryException;
import localdbms.database.exception.TableException;
import org.json.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TableImpl implements Table {
    private String location;
    private String name;
    private List<DataType> types;
    private List<Entry> entries;
    private EntryFactory entryFactory = EntryImpl::new;

    public TableImpl() throws EntryException {
        this("", "");
    }

    public TableImpl(String name, String location, DataType... columnTypes) throws EntryException {
        this.location = location;
        this.name = name;
        this.types = Arrays.asList(columnTypes);
        String readData = new JSONArray().toString();
        if (Tables.isTableExists(name, location)) {
            readData = readTableFromStorage(location + name);
        }
        this.entries = getEntriesFromJson(new JSONArray(readData), this.types);
    }

    private String readTableFromStorage(String storageLocation) {
        try (BufferedReader reader = new BufferedReader(new FileReader(storageLocation))) {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can not read table from storage", e);
        }
    }

    private List<Entry> getEntriesFromJson(JSONArray jsonArray, List<DataType> types) throws EntryException {
        List<Entry> entries = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject)json;
            entries.add(entryFactory.getEntryFromJson(jsonObject, types));
        }
        return entries;
    }

    @Override
    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    @Override
    public void writeToFile() throws EntryException, TableException {
        if (this.name.equals("") || this.location.equals("") ) {
            throw new TableException("Expected defined name, location and types");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.location + this.name))) {
            writer.write(this.getJsonArray().toString());
            writer.flush();
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

    @Override
    public void addRows(Entry... rows) {
        entries.addAll(Arrays.asList(rows));
    }

    @Override
    public void addRow(List<Object> values) throws TableException, EntryException {
        if (values.size() != types.size()) {
            throw new TableException("Expected " + types.size() + " values but " + values.size() + " found");
        }
        Entry entry = new EntryImpl(values, types);
        entries.add(entry);
    }

    @Override
    public void sort(int fieldNumber) {
        entries.sort(new Comparator<Entry>() {
            @Override
            @SuppressWarnings("unchecked")
            public int compare(Entry entry1, Entry entry2) {
                Comparable cmp1 = getComparableElement(entry1);
                Comparable cmp2 = getComparableElement(entry2);
                return cmp1.compareTo(cmp2);
            }
            private Comparable getComparableElement(Entry entry) {
                Comparable field = (Comparable)entry.getValues().get(fieldNumber);
                return (field instanceof Character || field instanceof String) ? field.toString().toLowerCase() : field;
            }
        });
    }

    @Override
    public List<Entry> getEntries() {
        return entries;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public List<DataType> getTypes() {
        return this.types;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void setTypes(DataType... types) {
        this.types = Arrays.asList(types);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setTypes(List<DataType> types) {
        this.types = types;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TableImpl)) return false;

        TableImpl table = (TableImpl) o;

        return (location != null ? location.equals(table.location) : table.location == null) &&
                (name != null ? name.equals(table.name) : table.name == null) &&
                (types != null ? types.equals(table.types) : table.types == null) &&
                (entries != null ? entries.equals(table.entries) : table.entries == null);
    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (types != null ? types.hashCode() : 0);
        result = 31 * result + (entries != null ? entries.hashCode() : 0);
        return result;
    }
}
