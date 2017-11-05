package common;

import DBMS.Dbms;
import DBMS.DbmsImpl;
import javax.naming.NamingException;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Bootstrap {

    public static void main(String[] args) throws NamingException, AlreadyBoundException, IOException {
        Dbms dbms = new DbmsImpl();

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("dbms", dbms);
    }

}
