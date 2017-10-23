package webdbms.DBMS.database;

import webdbms.DBMS.datatype.DataType;
import webdbms.DBMS.table.Table;
import webdbms.DBMS.exception.StorageException;

import java.util.List;

public interface Database {

    void save() throws StorageException;
    void delete() throws StorageException;

    boolean doesTableExist(String tableName);

    Table createTable(String name, DataType... columnTypes) throws StorageException;
    void deleteTable(String name) throws StorageException;

    List<Table> getTables();

    String getName();
    void setName(String name) throws StorageException;

    void loadTablesFromStorage() throws StorageException;
}
