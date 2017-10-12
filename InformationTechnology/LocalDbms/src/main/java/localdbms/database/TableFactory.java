package localdbms.database;

import localdbms.database.exception.EntryException;

@FunctionalInterface
public interface TableFactory {
    Table getTable() throws EntryException;
}
