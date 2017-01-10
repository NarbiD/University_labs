package ClientFunctions;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by NarbiD on 10.01.2017.
 */

abstract class Client {
    protected static String address = "127.0.0.1";
    protected static Integer serverPort = null;
    protected static Socket socket = null;

    protected static boolean connectToServer() {
        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            socket = new Socket(ipAddress, serverPort);
            System.out.println("IP address " + address + " and port " + serverPort + "was opened");
        } catch (IOException e) {
            System.err.println("Could not create a connection!");
            return false;
        }
        return true;
    }

    protected static void dataProcessing(){
    }
}
