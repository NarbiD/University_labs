package webdbms.DBMS;

import webdbms.DBMS.DataType;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class Entry {

    private List<DataType> types;
    private List<Object> values;

    private String file;

    public Entry(List<Object> values, List<DataType> types, IntegerInvlConstraint constraint) throws Exception {
        if (types.size() != values.size()) {
            throw new Exception("Expected types.size() == values.size() but types size == " +
                    types.size() + " and values.size() == " + values.size() + " found");
        }
        for (int column = 0; column < values.size(); column++) {
            if (!TypeManager.instanceOf(values.get(column), types.get(column), constraint)) {
                throw new Exception("Invalid type in table cell. Expected type " + types.get(column) +
                        " in column " + (column+1) + " but value " + values.get(column) +
                        " (" + values.get(column).getClass() + ") found");
            }
        }
        this.types = types;
        this.values = values;
    }

    public Entry(JSONObject json, List<DataType> types, IntegerInvlConstraint constraint) throws Exception {
        this(getValuesFromJson(json, types), types, constraint);
    }

    private static List<Object> getValuesFromJson(JSONObject json, List<DataType> types) {
        List<Object> localValues = new ArrayList<>();
        TreeMap<String, Object> jsonMap = new TreeMap<>(Comparator.comparing(Integer::valueOf));
        jsonMap.putAll(json.toMap());
        jsonMap.forEach((key, value) -> localValues.add(value));
        for (int i = 0; i < localValues.size(); i++) {
            localValues.set(i, TypeManager.parseObjectByType(localValues.get(i).toString(), types.get(i)));
        }
        return localValues;
    }

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        for (Integer column = 0; column < this.values.size(); column++) {
            json.put(column.toString(), this.values.get(column));
        }
        return json;
    }

    public List<Object> getValues() {
        return this.values;
    }

    public String toString() {
        return "Entry{" +
                "types=" + types +
                ", values=" + values +
                '}';
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry entry = (Entry) o;

        boolean typesIsEqual = types != null ? types.equals(entry.types) : entry.types == null;
        boolean valuesIsEqual = values != null ? values.equals(entry.values) : entry.values == null;

        return typesIsEqual && valuesIsEqual;
    }

    public int hashCode() {
        int result = types != null ? types.hashCode() : 0;
        result = 31 * result + (values != null ? values.hashCode() : 0);
        return result;
    }


    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
