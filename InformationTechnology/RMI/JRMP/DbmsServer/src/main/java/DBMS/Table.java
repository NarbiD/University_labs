package DBMS;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Table extends Remote {

     void addRow(List<Object> values, String file) throws Exception;

     void setRow(int entryIndex, List<Object> values, String file) throws Exception;

     void deleteRow(int rowNumber) throws RemoteException;

     List<String> getColumnNames() throws RemoteException;

     List<Entry> getEntries() throws RemoteException;

     String getName() throws RemoteException;

     List<DataType> getTypes() throws RemoteException;

    void setConstraint(int min, int max) throws RemoteException;

    void writeToFile() throws Exception;

    void loadDataFromFile() throws Exception;

    void delete() throws Exception;
}
