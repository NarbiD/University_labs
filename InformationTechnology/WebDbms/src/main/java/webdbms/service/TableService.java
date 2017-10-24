package webdbms.service;

import webdbms.DBMS.database.Database;
import webdbms.DBMS.database.Databases;
import webdbms.DBMS.datatype.DataType;
import webdbms.DBMS.datatype.constraint.RealConstraint;
import webdbms.DBMS.exception.StorageException;
import webdbms.DBMS.table.Table;
import webdbms.DBMS.table.TableFactory;
import webdbms.DBMS.table.TableImpl;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;


public class TableService {

    private TableFactory tableFactory = TableImpl::new;
    private Database database;

    public TableService(Database database) {
        this.database = database;
    }

    public void initTables() {
        database.getTables().addAll(database.getTables());
    }

    public void deleteTable(int tableIndex) throws StorageException {
        database.deleteTable(database.getTables().get(tableIndex).getName());
        database.getTables().remove(tableIndex);
    }

    public void createTable(String name, List<DataType> types, List<String> columnNames, RealConstraint constraint) throws StorageException {
        Database db = database;
        Table table = db.createTable(name);
        table.setTypes(types);
        table.setColumnNames(columnNames);
        table.setConstraint(constraint);
        validateProperties(table);
        database.getTables().add(table);
        db.save();
    }

    private void validateProperties(Table table) {
        if (table.getName().equals("")) {
            throw new IllegalArgumentException("Required name for the table");
        }
    }

    private void saveTables() throws StorageException {
        database.getTables().clear();
        database.getTables().addAll(database.getTables());
        database.save();
    }

    public List<Table> getTables() {
        return database.getTables();
    }

    public Table getTable(int tableIndex) {
        return database.getTables().get(tableIndex);
    }

    public Table findByName(String name) throws StorageException {
        String location = Databases.ABS_DEFAULT_LOCATION;
        Table table = tableFactory.getTable();
        table.setName(name);
        table.setLocation(location + database.getName() + File.separator);
        int tableIndex = database.getTables().indexOf(table);
        return database.getTables().get(tableIndex);
    }
}
