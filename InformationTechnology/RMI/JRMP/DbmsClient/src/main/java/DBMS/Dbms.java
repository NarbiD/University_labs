package DBMS;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

public interface Dbms extends Remote {
     void setDatabases(List<Database> databases) throws RemoteException;

     void loadDatabaseFromStorage() throws Exception;

     Database createDatabase(String name) throws Exception;

     void deleteDatabase(String name) throws Exception;

     Optional<Database> getDatabase(String name) throws RemoteException;

     List<Database> getAllDatabases() throws RemoteException;
}
