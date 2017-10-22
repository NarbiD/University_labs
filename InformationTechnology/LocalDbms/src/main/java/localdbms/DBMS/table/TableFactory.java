package localdbms.DBMS.table;

import localdbms.DBMS.exception.StorageException;

@FunctionalInterface
public interface TableFactory {
    Table getTable() throws StorageException;
}
