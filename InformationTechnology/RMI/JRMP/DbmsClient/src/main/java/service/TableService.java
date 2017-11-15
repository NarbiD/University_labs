package service;

import DBMS.*;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class TableService {

    private ObservableList<Database> databases;
    private ObservableList<Table> tables;
    private IntegerProperty dbIndex;

    public void initTables() throws RemoteException {
        tables.clear();
        tables.addAll(databases.get(dbIndex.get()).getTables());
    }

    public void deleteTable(int tableIndex) throws Exception {
        databases.get(dbIndex.get()).deleteTable(tables.get(tableIndex).getName());
        tables.remove(tableIndex);
    }

    public void createTable(String name, List<DataType> types, List<String> columnNames, IntegerInvlConstraint constraint) throws Exception {
        Database db = new ArrayList<>(databases).get(dbIndex.get());
        Table table = db.createTable(name, types, columnNames, constraint.getMinValue(), constraint.getMaxValue());
        validateProperties(table);
        tables.add(table);
        db.save();
    }

    public List<Integer> search(int tableIndex, List<Object> objects) throws RemoteException {
        List<Integer> entriesNums = new ArrayList<>();
        List<Entry> entries = tables.get(tableIndex).getEntries();
        for (int j = 0; j < entries.size(); j++) {
            Entry entry = entries.get(j);
            if (isValuesEquals(entry.getValues(), objects)) {
                entriesNums.add(j);
            }
        }
        return entriesNums;
    }

    public boolean isValuesEquals(List<Object> v1, List<Object> v2) {
        for (int i = 0; i < v1.size(); i++) {
            boolean cnd1 =  v1.get(i).equals(v2.get(i) instanceof Character ?(v2.get(i)).toString():v2.get(i));
            if (!(cnd1 || v2.get(i) == null)) {
                return false;
            }
        }
        return true;
    }

    private void validateProperties(Table table) throws RemoteException {
        if (table.getName().equals("")) {
            throw new IllegalArgumentException("Required name for the table");
        } else if (table.getColumnNames().isEmpty()) {
            throw new IllegalArgumentException("Required at least one column");
        }
    }

    public void addRow(int tableIndex, List<Object> values, String file) throws Exception {
        Table table = getTable(tableIndex);
        table.addRow(values, file);
        saveTables();
    }

    public void setRow(int entryIndex, int tableIndex, List<Object> values, String file) throws Exception {
        Table table = getTable(tableIndex);
        table.setRow(entryIndex, values, file);
        saveTables();
    }

    private void saveTables() throws Exception {
        databases.get(dbIndex.get()).getTables().clear();
        databases.get(dbIndex.get()).getTables().addAll(tables);
        databases.get(dbIndex.get()).save();
    }

    public ObservableList<Table> getTables() {
        return tables;
    }

    public void setTables(ObservableList<Table> tables) {
        this.tables = tables;
    }

    public void setDatabases(ObservableList<Database> databases) {
        this.databases = databases;
    }

    public Table getTable(int tableIndex) {
        return tables.get(tableIndex);
    }

    public void setDbIndex(IntegerProperty dbIndex) {
        this.dbIndex = dbIndex;
    }
}