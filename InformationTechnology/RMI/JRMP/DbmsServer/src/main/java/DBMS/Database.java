package DBMS;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Database extends Remote {
    void loadTablesFromStorage() throws Exception;

     void setName(String name) throws Exception;

     void addTable(Table table) throws Exception;

     Table createTable(String name, List<DataType> types, List<String> columnNames,
                   IntegerInvlConstraint constraint) throws Exception;

     Table createTable(String name, List<DataType> types, List<String> columnNames,
                   int min, int max) throws Exception;

     List<Table> getTables() throws RemoteException;

     String getName() throws RemoteException;

     void deleteTable(String name) throws Exception;

     void save() throws Exception;

     void delete() throws Exception;

}
