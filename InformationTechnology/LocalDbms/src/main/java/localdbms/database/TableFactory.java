package localdbms.database;

import localdbms.database.exception.EntryException;
import localdbms.database.exception.TableException;

@FunctionalInterface
public interface TableFactory {
    Table getTable() throws EntryException, TableException;
}
