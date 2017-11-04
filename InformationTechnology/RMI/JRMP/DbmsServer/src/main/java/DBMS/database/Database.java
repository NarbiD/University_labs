package DBMS.database;

import common.DataType;
import DBMS.table.Table;
import DBMS.exception.EntryException;
import DBMS.exception.StorageException;
import DBMS.exception.TableException;

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
