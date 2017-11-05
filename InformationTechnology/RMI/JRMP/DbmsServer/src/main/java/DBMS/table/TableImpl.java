package DBMS.table;

import DBMS.entry.Entry;
import DBMS.entry.EntryFactory;
import DBMS.entry.EntryImpl;
import DBMS.datatype.constraint.RealConstraint;
import common.DataType;
import org.json.*;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class TableImpl extends UnicastRemoteObject implements Table {
    @Override
    public RealConstraint getConstraint() {
        return constraint;
    }

    private RealConstraint constraint;
    private String location;
    private String name;
    private List<DataType> types;
    private List<Entry> entries;
    private EntryFactory entryFactory;
    private List<String> columnNames;

    public TableImpl() throws IOException {
        this("", "");
    }

    public TableImpl(String name, String location, DataType... columnTypes) throws IOException {
        this.location = location;
        this.name = name;
        this.types = Arrays.asList(columnTypes);
        this.columnNames = new ArrayList<>();
        this.constraint = new RealConstraint();
        this.entryFactory = (EntryFactory & Serializable) EntryImpl::new;
        try {
            loadDataFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadDataFromFile() throws IOException {
        String rowInFile = new JSONArray().toString();
        String readData = rowInFile + '\n' + rowInFile + '\n' + rowInFile + '\n' + rowInFile + '\n' + rowInFile;
        if (Tables.isTableExists(this.name, this.location)) {
            readData = readTableFromStorage(this.location + this.name);
        }
        String[] s = readData.split("\n");
        List<DataType> readTypes = new ArrayList<>();
        for (Object title : new JSONArray(s[0]).toList()) {
            String t = title.toString();
            columnNames.add(t);
        }
        new JSONArray(s[1]).toList().forEach(type -> readTypes.add(DataType.valueOf(type.toString())));
        JSONArray constraints = new JSONArray(s[3]);
        if (constraints.length() != 0) {
            this.constraint = new RealConstraint(constraints.getDouble(0), constraints.getDouble(1));
        }
        JSONArray ByteArrayJson = new JSONArray(s[4]);
        if (this.types.equals(readTypes) || types.isEmpty()) {
            this.types = readTypes;
            this.entries = getEntriesFromJson(new JSONArray(s[2]), readTypes);
            for (int i = 0; i < entries.size(); i++) {
                String bytes = ByteArrayJson.get(i).toString();
                this.entries.get(i).setImage(bytes);
            }
        } else {
            throw new IOException("Expected types " + readData + " but " + this.types + " found");
        }

    }

    private String readTableFromStorage(String storageLocation) {
        try (BufferedReader reader = new BufferedReader(new FileReader(storageLocation))) {
            return reader.readLine() + '\n' + reader.readLine() + '\n' + reader.readLine() + '\n' + reader.readLine() + '\n' + reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can not read table from storage", e);
        }
    }

    private List<Entry> getEntriesFromJson(JSONArray jsonArray, List<DataType> types) throws IOException {
        List<Entry> entries = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject)json;
            Entry entry = entryFactory.getEntryFromJson(jsonObject, types, constraint);
            entries.add(entry);
        }
        return entries;
    }

    @Override
    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    @Override
    public void writeToFile() throws IOException {
        if (this.name.equals("") || this.location.equals("") ) {
            throw new IOException("Expected defined name, location and types");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.location + this.name))) {
            writer.write(new JSONArray(this.columnNames).toString() + "\n");
            writer.write(new JSONArray(this.types).toString() + "\n");
            writer.write(this.getJsonArray().toString() + "\n");
            JSONArray constraints = new JSONArray();
            if (constraint.isDefined()) {
                constraints.put(this.constraint.getMinValue());
                constraints.put(this.constraint.getMaxValue());
            }
            writer.write(constraints.toString() + "\n");
            List<String> images = new ArrayList<>();
            entries.forEach(entry -> {
                try {
                    images.add(entry.getImage());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            });
            JSONArray pics = new JSONArray(images);
            writer.write(pics.toString());
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException("Can not write table to storage", e);
        }
    }

    private JSONArray getJsonArray() throws IOException {
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
    public void addRow(List<Object> values, String image) throws IOException {
        if (values.size() != types.size()) {
            throw new IOException("Expected " + types.size() + " values but " + values.size() + " found");
        }
        Entry entry = new EntryImpl(values, types, constraint);
        if (image != null) {
            entry.setImage(image);
        }
        entries.add(entry);
    }

    @Override
    public void sort(int fieldNumber) {
        entries.sort(new Comparator<Entry>() {
            @Override
            @SuppressWarnings("unchecked")
            public int compare(Entry entry1, Entry entry2) {
                try {
                    Comparable cmp1 = getComparableElement(entry1);
                    Comparable cmp2 = getComparableElement(entry2);
                    return cmp1.compareTo(cmp2);
                } catch (RemoteException e){
                    throw new RuntimeException(e);
                }
            }
            private Comparable getComparableElement(Entry entry) throws RemoteException {
                Comparable field = (Comparable)entry.getValues().get(fieldNumber);
                return (field instanceof Character || field instanceof String) ? field.toString().toLowerCase() : field;
            }
        });
    }

    @Override
    public List<String> getColumnNames() {
        return columnNames;
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

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void setTypes(DataType... types) throws RemoteException {
        this.types = Arrays.asList(types);
    }

    @Override
    public void setColumnNames(List<String> names) {
        this.columnNames = names;
    }

    @Override
    public void setTypes(List<DataType> types) throws RemoteException {
        this.types = types;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setConstraint(double min, double max) throws RemoteException {
        this.constraint = new RealConstraint(min, max);
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
