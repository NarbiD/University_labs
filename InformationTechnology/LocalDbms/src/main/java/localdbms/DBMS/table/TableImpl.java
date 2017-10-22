package localdbms.DBMS.table;

import localdbms.DBMS.entry.Entry;
import localdbms.DBMS.entry.EntryFactory;
import localdbms.DBMS.entry.EntryImpl;
import localdbms.DBMS.datatype.constraint.RealConstraint;
import localdbms.DataType;
import localdbms.DBMS.exception.EntryException;
import localdbms.DBMS.exception.TableException;
import org.json.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

public class TableImpl implements Table {
    private RealConstraint constraint;
    private String location;
    private String name;
    private List<DataType> types;
    private List<Entry> entries;
    private EntryFactory entryFactory = EntryImpl::new;

    private List<String> columnNames;

    public TableImpl() throws EntryException, TableException {
        this("", "");
    }

    public TableImpl(String name, String location, DataType... columnTypes) throws EntryException, TableException {
        this.location = location;
        this.name = name;
        this.types = Arrays.asList(columnTypes);
        this.columnNames = new ArrayList<>();
        this.constraint = new RealConstraint();
        try {
            loadDataFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadDataFromFile() throws EntryException, TableException, IOException {
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
                byte[] bytes = JSONtoByteArray((JSONArray)ByteArrayJson.get(i));
                entries.get(i).setImage(ImageIO.read(new ByteArrayInputStream(bytes)));
                System.out.println(Arrays.toString(bytes));
//                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(b);
//                try {
//                    entries.get(i).setImage(ImageIO.read(byteArrayInputStream));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        } else {
            throw new TableException("Expected types " + readData + " but " + this.types + " found");
        }

    }

    private String readTableFromStorage(String storageLocation) {
        try (BufferedReader reader = new BufferedReader(new FileReader(storageLocation))) {
            return reader.readLine() + '\n' + reader.readLine() + '\n' + reader.readLine() + '\n' + reader.readLine() + '\n' + reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Can not read table from storage", e);
        }
    }

    private List<Entry> getEntriesFromJson(JSONArray jsonArray, List<DataType> types) throws EntryException {
        List<Entry> entries = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject jsonObject = (JSONObject)json;
            Entry entry = entryFactory.getEntryFromJson(jsonObject, types, constraint);
            entries.add(entry);
        }
        return entries;
    }

    private byte[] JSONtoByteArray(JSONArray json) {
        List<Object> ints = json.toList();
        byte[] b = new byte[ints.size()];
        IntStream.range(0, ints.size()).forEach(i -> b[i] = (byte) ((int) ints.get(i)));
        return b;
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
            List<byte[]> byteArraysWithImages = new ArrayList<>();
            entries.forEach(entry -> {
                try {
                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    ImageIO.write(entry.getImage(), "png", byteArray);
                    byteArraysWithImages.add(byteArray.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.write(new JSONArray(this.columnNames).toString() + "\n");
            writer.write(new JSONArray(this.types).toString() + "\n");
            writer.write(this.getJsonArray().toString() + "\n");
            JSONArray constraints = new JSONArray();
            if (constraint.isDefined()) {
                constraints.put(this.constraint.getMinValue());
                constraints.put(this.constraint.getMaxValue());
            }
            writer.write(constraints.toString() + "\n");
            JSONArray pics = new JSONArray(byteArraysWithImages);
            writer.write(pics.toString());
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
    public void addRow(List<Object> values, File pic) throws TableException, EntryException {
        if (values.size() != types.size()) {
            throw new TableException("Expected " + types.size() + " values but " + values.size() + " found");
        }
        Entry entry = new EntryImpl(values, types, constraint);
        try {
            entry.setImage(ImageIO.read(pic));
        } catch (IOException e) {
            e.printStackTrace();
        }
        entries.add(entry);
    }

    @Override
    public void addRow(List<Object> values) throws TableException, EntryException {
        if (values.size() != types.size()) {
            throw new TableException("Expected " + types.size() + " values but " + values.size() + " found");
        }
        Entry entry = new EntryImpl(values, types, constraint);
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
    public void setTypes(DataType... types) {
        this.types = Arrays.asList(types);
    }

    @Override
    public void setColumnNames(List<String> names) {
        this.columnNames = names;
    }

    @Override
    public void setTypes(List<DataType> types) {
        this.types = types;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setConstraint(RealConstraint constraint) {
        this.constraint = constraint;
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