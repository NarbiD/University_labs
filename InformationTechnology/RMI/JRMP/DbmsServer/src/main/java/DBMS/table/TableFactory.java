package DBMS.table;

import DBMS.exception.StorageException;

@FunctionalInterface
public interface TableFactory {
    Table getTable() throws StorageException;
}
