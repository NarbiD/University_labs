package localdbms.DBMS.table;

import localdbms.DataType;
import localdbms.DBMS.entry.Entry;
import localdbms.DBMS.datatype.constraint.RealConstraint;
import localdbms.DBMS.exception.EntryException;
import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.exception.TableException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Table {

    String getName();
    List<DataType> getTypes();

    List<String> getColumnNames();

    List<Entry> getEntries();

    void addRows(Entry... rows);

    void addRow(List<Object> values, File pic) throws TableException, EntryException;

    void addRow(List<Object> values) throws StorageException;
    void sort(int fieldNumber);

    void writeToFile() throws StorageException;
    void loadDataFromFile() throws EntryException, TableException, IOException;
    boolean isEmpty();

    void setTypes(List<DataType> types);

    void setName(String name);
    void setLocation(String location);
    void setTypes(DataType... types);

    void setColumnNames(List<String> names);

    void setConstraint(RealConstraint realIntervalConstraint);
}
