package localdbms.service;

import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import localdbms.DBMS.database.Database;
import localdbms.DBMS.exception.EntryException;
import localdbms.DBMS.exception.TableException;
import localdbms.DataType;
import localdbms.DBMS.datatype.constraint.RealConstraint;
import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.table.Table;

import java.io.File;
import java.util.List;


public class TableService {

    public void setDatabases(ObservableList<Database> databases) {
        this.databases = databases;
    }

    private ObservableList<Database> databases;
    private ObservableList<Table> tables;
    private IntegerProperty dbIndex;


    public ObservableList<Table> getTables() {
        tables = FXCollections.observableArrayList(databases.get(dbIndex.get()).getTables());
        return tables;
    }

    public TableService() {
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
        if (name.equals("")) {
            throw new IllegalArgumentException("Required name for the table");
        } else if (columnNames.isEmpty()) {
            throw new IllegalArgumentException("Required at least one column");
        }
        tables.add(table);
        db.save();
    }

    public void addRow(int tableIndex, List<Object> values, File image) throws StorageException {
        Table table = getTable(tableIndex);
        if (image != null) {
            table.addRow(values, image);
        } else {
            table.addRow(values);
        }
        databases.get(dbIndex.get()).getTables().clear();
        databases.get(dbIndex.get()).getTables().addAll(tables);
        databases.get(dbIndex.get()).save();
    }

    public Table getTable(int tableIndex) {
        return tables.get(tableIndex);
    }

    public void setDbIndex(IntegerProperty dbIndex) {
        this.dbIndex = dbIndex;
    }
}
