package DBMS;

import org.json.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Entry extends Remote {

     JSONObject getJson() throws RemoteException;

     List<Object> getValues() throws RemoteException;

     String getFile() throws RemoteException;

     void setFile(String file) throws RemoteException;
}
