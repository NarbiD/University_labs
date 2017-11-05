package DBMS.database;

import common.DataType;
import DBMS.table.Table;
import java.io.IOException;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Database extends Serializable, Remote {

    void save() throws IOException;
    void delete() throws IOException;

    boolean doesTableExist(String tableName) throws RemoteException;

    Table createTable(String name, DataType... columnTypes) throws IOException;
    void deleteTable(String name) throws IOException;

    List<Table> getTables() throws RemoteException;

    String getName()throws RemoteException;
    void setName(String name) throws IOException;

    void loadTablesFromStorage() throws IOException;
}
