package localdbms.DBMS.table;

import localdbms.DBMS.exception.EntryException;
import localdbms.DBMS.exception.TableException;

@FunctionalInterface
public interface TableFactory {
    Table getTable() throws EntryException, TableException;
}
