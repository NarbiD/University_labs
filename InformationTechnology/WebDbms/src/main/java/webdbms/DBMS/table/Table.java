package webdbms.DBMS.table;

import webdbms.DBMS.datatype.DataType;
import webdbms.DBMS.entry.Entry;
import webdbms.DBMS.datatype.constraint.RealConstraint;
import webdbms.DBMS.exception.StorageException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public interface Table {

    String getName();
    List<DataType> getTypes();

    List<String> getColumnNames();

    List<Entry> getEntries();

    void addRows(Entry... rows);

    void addRow(List<Object> values, BufferedImage image) throws StorageException;

    default void deleteRow(int rowNumber) {
        throw new UnsupportedOperationException();
    }

    void sort(int fieldNumber);

    void writeToFile() throws StorageException;

    RealConstraint getConstraint();

    void loadDataFromFile() throws StorageException, IOException;
    boolean isEmpty();

    void setTypes(List<DataType> types);

    void setName(String name);
    void setLocation(String location);
    void setTypes(DataType... types);

    void setColumnNames(List<String> names);

    void setConstraint(RealConstraint realIntervalConstraint);
}
