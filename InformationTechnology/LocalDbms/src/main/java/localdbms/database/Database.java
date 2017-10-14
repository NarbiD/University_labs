package localdbms.database;

import localdbms.database.exception.EntryException;
import localdbms.database.exception.StorageException;

import java.util.Set;

public interface Database {

    void save() throws StorageException;
    void delete() throws StorageException;

    boolean doesTableExist(String tableName);

    Table createTable(String name, DataType... columnTypes) throws EntryException;
    void deleteTable(String name) throws StorageException;

    Set<Table> getTables();

    String getName();
    void setName(String name) throws StorageException;
    String getLocation();

}
