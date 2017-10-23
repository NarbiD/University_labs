package webdbms.service;

import javafx.beans.property.IntegerProperty;
import javafx.collections.ObservableList;
import webdbms.DBMS.database.Database;
import webdbms.DBMS.datatype.DataType;
import webdbms.DBMS.datatype.constraint.RealConstraint;
import webdbms.DBMS.exception.StorageException;
import webdbms.DBMS.table.Table;

import java.awt.image.BufferedImage;
import java.util.List;


public class TableService {

    private ObservableList<Database> databases;
    private ObservableList<Table> tables;
    private IntegerProperty dbIndex;

    public void initTables() {
        tables.clear();
        tables.addAll(databases.get(dbIndex.get()).getTables());
    }

    public void deleteTable(int tableIndex) throws StorageException {
        databases.get(dbIndex.get()).deleteTable(tables.get(tableIndex).getName());
        tables.remove(tableIndex);
    }

    public void createTable(String name, List<DataType> types, List<String> columnNames, RealConstraint constraint) throws StorageException {
        Database db = databases.get(dbIndex.get());
        Table table = db.createTable(name);
        table.setTypes(types);
        table.setColumnNames(columnNames);
        table.setConstraint(constraint);
        validateProperties(table);
        tables.add(table);
        db.save();
    }

    private void validateProperties(Table table) {
        if (table.getName().equals("")) {
            throw new IllegalArgumentException("Required name for the table");
        } else if (table.getColumnNames().isEmpty()) {
            throw new IllegalArgumentException("Required at least one column");
        }
    }

    public void addRow(int tableIndex, List<Object> values, BufferedImage image) throws StorageException {
        Table table = getTable(tableIndex);
        table.addRow(values, image);
        saveTables();
    }

    private void saveTables() throws StorageException {
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
