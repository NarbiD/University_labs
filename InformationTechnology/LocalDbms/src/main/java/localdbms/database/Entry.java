package localdbms.database;


import org.json.JSONObject;

import java.util.*;

public class Entry {
    private List<DataType> types;



    public List<DataType> getTypes() {

        return types;
    }

    public void setTypes(List<DataType> types) {
        this.types = types;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    private List<Object> values;

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

        if (types != null ? !types.equals(entry.types) : entry.types != null) return false;
        return values != null ? values.equals(entry.values) : entry.values == null;
    }

    @Override
    public int hashCode() {
        int result = types != null ? types.hashCode() : 0;
        result = 31 * result + (values != null ? values.hashCode() : 0);
        return result;
    }

    public Entry(List<DataType> types, List<Object> values) {
        this.types = types;
        this.values = values;
    }

    public Entry() {
        types = new ArrayList<>();
        values = new ArrayList<>();
    }

    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        for (Integer count=0; count < values.size(); count++) {
            if (TypeChecker.instanceOf(values.get(count), types.get(count))) {
                json.put(count.toString(), values.get(count));
            } else {
                throw new RuntimeException();
            }
        }
        return json;
    }

    public void putJson(JSONObject json, List<DataType> types) {
        this.types = types;
        TreeMap<String, Object> jsonMap = new TreeMap<>(json.toMap());
        for (Map.Entry<String, Object> jsonEntry : jsonMap.entrySet()) {
            Object data = jsonEntry.getValue();
            int c = Integer.parseInt(jsonEntry.getKey());
            if (TypeChecker.instanceOf(data, types.get(c))){
                values.add(data);
            }
        }
    }
}
