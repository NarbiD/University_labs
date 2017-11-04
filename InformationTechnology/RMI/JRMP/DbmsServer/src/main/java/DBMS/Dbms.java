package DBMS;

import DBMS.database.Database;
import DBMS.exception.StorageException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Optional;

public interface Dbms extends Remote {
    void setDatabases(Collection<Database> databases)throws RemoteException;
    void loadDatabaseFromStorage() throws StorageException, RemoteException;
    Database createDatabase(String name) throws StorageException, RemoteException;
    void deleteDatabase(String name) throws StorageException, RemoteException;
    Optional<Database> getDatabase(String name) throws RemoteException;
    Collection<Database> getAllDatabases() throws RemoteException;
}
