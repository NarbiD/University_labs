package localdbms.DBMS.table;

import localdbms.DataType;
import localdbms.DBMS.entry.Entry;
import localdbms.DBMS.datatype.constraint.RealConstraint;
import localdbms.DBMS.exception.StorageException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface Table {

    String getName();
    List<DataType> getTypes();

    List<String> getColumnNames();

    List<Entry> getEntries();

    void addRows(Entry... rows);

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    void addRow(List<Object> values, Optional<BufferedImage> image) throws StorageException;

    void sort(int fieldNumber);

    void writeToFile() throws StorageException;
    void loadDataFromFile() throws StorageException, IOException;
    boolean isEmpty();

    void setTypes(List<DataType> types);

    void setName(String name);
    void setLocation(String location);
    void setTypes(DataType... types);

    void setColumnNames(List<String> names);

    void setConstraint(RealConstraint realIntervalConstraint);
}
