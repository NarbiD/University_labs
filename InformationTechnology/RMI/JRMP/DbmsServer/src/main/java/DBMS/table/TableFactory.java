package DBMS.table;

import java.io.IOException;

@FunctionalInterface
public interface TableFactory {
    Table getTable() throws IOException;
}
