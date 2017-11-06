package DBMS;

import org.json.JSONObject;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Entry extends Remote {

    public JSONObject getJson() throws RemoteException;

    public List<Object> getValues() throws RemoteException;

    public String getFile() throws RemoteException;

    public void setFile(String file) throws RemoteException;
}
