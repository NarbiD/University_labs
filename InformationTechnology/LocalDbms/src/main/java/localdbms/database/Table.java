package localdbms.database;

import localdbms.database.exception.StorageException;

import java.util.List;

public interface Table {

    String getName();
    List<DataType> getTypes();
    List<Entry> getEntries();

    void addRows(EntryImpl... rows);
    void sort(int fieldNumber);

    void writeToFile() throws StorageException;
    boolean isEmpty();

    void setName(String name);
    void setLocation(String location);
    void setTypes(DataType... types);
}
