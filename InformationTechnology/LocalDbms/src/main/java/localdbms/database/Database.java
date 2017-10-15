package localdbms.database;

import localdbms.database.exception.EntryException;
import localdbms.database.exception.StorageException;
import localdbms.database.exception.TableException;

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
    String getLocation();

    void loadTablesFromStorage() throws StorageException;
}
