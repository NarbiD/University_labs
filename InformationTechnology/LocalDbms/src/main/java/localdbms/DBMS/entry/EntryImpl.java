package localdbms.DBMS.entry;

import localdbms.DataType;
import org.json.JSONObject;
import localdbms.DBMS.datatype.TypeManager;
import localdbms.DBMS.exception.EntryException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

public class EntryImpl implements Entry {

    private List<DataType> types;
    private List<Object> values;

    public EntryImpl(List<Object> values, List<DataType> types) throws EntryException {
        if (types.size() != values.size()) {
            throw new EntryException("Expected types.size() == values.size() but types size == " +
                    types.size() + " and values.size() == " + values.size() + " found");
        }
        for (int column = 0; column < values.size(); column++) {
            if (!TypeManager.instanceOf(values.get(column), types.get(column))) {
                throw new EntryException("Invalid type in table cell. Expected type " + types.get(column) +
                        " in column " + (column+1) + " but value " + values.get(column) +
                        " (" + values.get(column).getClass() + ") found");
            }
        }
        this.types = types;
        this.values = values;
    }

    public EntryImpl(JSONObject json, List<DataType> types) throws EntryException {
        this(getValuesFromJson(json), types);
    }

    private static List<Object> getValuesFromJson(JSONObject json) {
        List<Object> localValues = new ArrayList<>();
        TreeMap<String, Object> jsonMap = new TreeMap<>(Comparator.comparing(Integer::valueOf));
        jsonMap.putAll(json.toMap());
        jsonMap.forEach((key, value) -> localValues.add(value));
        return localValues;
    }

    @Override
    public JSONObject getJson() {
        JSONObject json = new JSONObject();
        for (Integer column = 0; column < this.values.size(); column++) {
            json.put(column.toString(), this.values.get(column));
        }
        return json;
    }

    @Override
    public List<Object> getValues() {
        return this.values;
    }

    @Override
    public String toString() {
        return "EntryImpl{" +
                "types=" + types +
                ", values=" + values +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntryImpl)) return false;
        EntryImpl entry = (EntryImpl) o;

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
