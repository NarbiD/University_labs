package localdbms.DBMS.database;

import localdbms.DataType;
import localdbms.DBMS.table.Table;
import localdbms.DBMS.exception.EntryException;
import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.exception.TableException;

import java.util.List;

public interface Database {

    void save() throws StorageException;
    void delete() throws StorageException;

    boolean doesTableExist(String tableName);

    Table createTable(String name, DataType... columnTypes) throws EntryException, TableException;
    void deleteTable(String name) throws StorageException;

    List<Table> getTables();

    String getName();
    void setName(String name) throws StorageException;

    void loadTablesFromStorage() throws StorageException;
}
