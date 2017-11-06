package common;

import DBMS.DbmsImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class Bootstrap {

    public static void main(String[] args) throws Exception {

        DbmsImpl dbms = new DbmsImpl();

        Registry registry = LocateRegistry.createRegistry(1099);
        registry.bind("dbms", dbms);

        System.out.println("Server started!");
    }

}
