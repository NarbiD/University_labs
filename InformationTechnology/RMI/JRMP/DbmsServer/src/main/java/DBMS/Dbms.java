package DBMS;

import DBMS.database.Database;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Optional;

public interface Dbms extends Remote, Serializable {
    void setDatabases(Collection<Database> databases) throws RemoteException;
    void loadDatabaseFromStorage() throws IOException, RemoteException;
    Database createDatabase(String name) throws IOException, RemoteException;
    void deleteDatabase(String name) throws IOException, RemoteException;
    Optional<Database> getDatabase(String name) throws RemoteException;
    Collection<Database> getAllDatabases() throws RemoteException;
}
