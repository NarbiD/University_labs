package DBMS.entry;

import common.DataType;
import org.json.JSONObject;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Entry extends Remote, Serializable {
    List<Object> getValues() throws RemoteException;
    String getImage() throws RemoteException;
    void setImage(String image) throws RemoteException;
    JSONObject getJson() throws RemoteException;
    List<DataType> getTypes() throws RemoteException;
}
