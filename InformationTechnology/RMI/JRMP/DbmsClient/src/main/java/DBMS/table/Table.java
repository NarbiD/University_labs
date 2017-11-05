package DBMS.table;

import common.DataType;
import DBMS.entry.Entry;
import DBMS.datatype.constraint.RealConstraint;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Table extends Serializable, Remote {

    String getName() throws RemoteException;
    List<DataType> getTypes() throws RemoteException;

    List<String> getColumnNames() throws RemoteException;

    List<Entry> getEntries() throws RemoteException;

    void addRows(Entry... rows) throws RemoteException;

    void addRow(List<Object> values, String image) throws IOException;

    void sort(int fieldNumber) throws RuntimeException, RemoteException;

    void writeToFile() throws IOException;

    RealConstraint getConstraint() throws RemoteException;

    void loadDataFromFile() throws IOException;
    boolean isEmpty() throws RemoteException;

    void setTypes(List<DataType> types) throws RemoteException;

    void setName(String name) throws RemoteException;
    void setLocation(String location) throws RemoteException;
    void setTypes(DataType... types) throws RemoteException;

    void setColumnNames(List<String> names)throws RemoteException;

    void setConstraint(double min, double max) throws RemoteException;
}
