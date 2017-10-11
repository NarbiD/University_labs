package localdbms.database;

import org.json.JSONObject;

import java.util.*;

public class Entry {
    private List<DataType> types;
    private List<Object> values;

    public Entry(List<DataType> types, List<Object> values) {
        this.types = types;
        this.values = values;
    }

    public Entry() {
        this.types = new ArrayList<>();
        this.values = new ArrayList<>();
    }

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        for (Integer columnNumber=0; columnNumber < this.values.size(); columnNumber++) {
            if (TypeChecker.instanceOf(this.values.get(columnNumber), this.types.get(columnNumber))) {
                json.put(columnNumber.toString(), this.values.get(columnNumber));
            } else {
                throw new RuntimeException();
            }
        }
        return json;
    }

    public void parseDataFromJson(JSONObject json, List<DataType> types) {
        this.types = types;
        TreeMap<String, Object> jsonMap = new TreeMap<>(json.toMap());
        for (Map.Entry<String, Object> jsonEntry : jsonMap.entrySet()) {
            Object data = jsonEntry.getValue();
            int c = Integer.parseInt(jsonEntry.getKey());
            if (TypeChecker.instanceOf(data, types.get(c))){
                this.values.add(data);
            }
        }
    }

    public List<DataType> getTypes() {
        return this.types;
    }

    public void setTypes(List<DataType> types) {
        this.types = types;
    }

    public List<Object> getValues() {
        return this.values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "types=" + types +
                ", values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entry)) return false;
        Entry entry = (Entry) o;

        boolean typesIsEqual = types != null ? types.equals(entry.types) : entry.types == null;
        boolean valuesIsEqual = values != null ? values.equals(entry.values) : entry.values == null;

        return typesIsEqual && valuesIsEqual;
    }

    @Override
    public int hashCode() {
        int result = types != null ? types.hashCode() : 0;
        result = 31 * result + (values != null ? values.hashCode() : 0);
        return result;
    }
}
