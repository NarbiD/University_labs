package service;

import DBMS.datatype.constraint.RealConstraint;
import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import DBMS.database.Database;
import java.io.IOException;
import DBMS.table.Table;
import common.DataType;

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

    public void deleteTable(int tableIndex) throws IOException {
        databases.get(dbIndex.get()).deleteTable(tables.get(tableIndex).getName());
        tables.remove(tableIndex);
    }

    public void createTable(String name, List<DataType> types, List<String> columnNames, RealConstraint constraint) throws IOException {
        Database db = new ArrayList<>(databases).get(dbIndex.get());
        Table table = db.createTable(name);
        table.setTypes(new ArrayList<>(types));
        table.setColumnNames(new ArrayList<>(columnNames));
        table.setConstraint(constraint.getMinValue(), constraint.getMaxValue());
        validateProperties(table);
        tables.add(table);
        db.save();
    }

    private void validateProperties(Table table) throws RemoteException {
        if (table.getName().equals("")) {
            throw new IllegalArgumentException("Required name for the table");
        } else if (table.getColumnNames().isEmpty()) {
            throw new IllegalArgumentException("Required at least one column");
        }
    }

    public void addRow(int tableIndex, List<Object> values, String image) throws IOException {
        Table table = getTable(tableIndex);
        table.addRow(values, image);
        saveTables();
    }

    private void saveTables() throws IOException {
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
