package localdbms.DBMS.table;

import localdbms.DBMS.entry.Entry;
import localdbms.DBMS.exception.StorageException;
import localdbms.DBMS.exception.TableException;
import localdbms.DataType;

import java.io.IOException;
import java.util.List;

public interface Table {

    interface Builder {

        Builder setName(String name);

        Builder setLocation(String location);

        Builder setTypes(List<DataType> dataTypes);

        Builder setColumnNames(List<String> names);

        Table build() throws StorageException;
    }

    String getName();
    List<DataType> getTypes();

    void delete() throws TableException;

    List<String> getColumnNames();

    List<Entry> getEntries();

    void addRow(List<Object> values) throws StorageException;

    default void deleteRow(int rowNumber) {
        throw new UnsupportedOperationException();
    }

    void sort(int fieldNumber);

    void writeToFile() throws StorageException;

    void loadDataFromFile() throws StorageException, IOException;

    String getLocation();

    TableImpl.Builder getBuilder() throws StorageException;

    boolean isEmpty();
}
