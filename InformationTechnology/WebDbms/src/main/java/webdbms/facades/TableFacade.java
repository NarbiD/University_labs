package webdbms.facades;

import webdbms.DBMS.DataType;
import webdbms.DBMS.IntegerInvlConstraint;
import webdbms.DBMS.Table;

import java.util.List;

public class TableFacade {

    private Table table;

    public TableFacade(Table table) {
        this.table = table;
    }

    public String getTableName() {
        return table.getName();
    }

    public int getNumberOfRows() {
        return table.getEntries().size();
    }

    public List<DataType> getColumnTypes() {
        return table.getTypes();
    }

    public List<String> getColumnNames() {
        return table.getColumnNames();
    }

    public IntegerInvlConstraint getIntegerInvlConstraint() {
        return table.getConstraint();
    }
}
