package localdbms.DBMS.database;

import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.table.Table;
import localdbms.DataType;

import java.io.IOException;
import java.util.List;

public interface Database {

    String getName();
    void setName(String name) throws StorageException;

    void save() throws StorageException;
    void delete() throws StorageException;

    Table createTable(String name, List<DataType> types, List<String> columnNames) throws StorageException;

    void deleteTable(String name) throws StorageException;
    List<Table> getTables();

    void loadTablesFromStorage() throws StorageException, IOException;
}
