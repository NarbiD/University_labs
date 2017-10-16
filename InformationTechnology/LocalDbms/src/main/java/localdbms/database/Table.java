package localdbms.database;

import localdbms.database.exception.EntryException;
import localdbms.database.exception.StorageException;
import localdbms.database.exception.TableException;

import java.util.List;

public interface Table {

    String getName();
    List<DataType> getTypes();

    List<String> getColumnNames();

    List<Entry> getEntries();
    String getLocation();

    void addRows(Entry... rows);
    void addRow(List<Object> values) throws StorageException;
    void sort(int fieldNumber);

    void writeToFile() throws StorageException;
    void loadDataFromFile() throws EntryException, TableException;
    boolean isEmpty();

    void setTypes(List<DataType> types);

    void setName(String name);
    void setLocation(String location);
    void setTypes(DataType... types);

    void setColumnNames(List<String> names);
}
