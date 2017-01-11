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

    public abstract double function(double x);

    protected boolean connectToServer() {
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

    public void dataProcessing(){
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();
             DataInputStream DataIn = new DataInputStream(in);
             DataOutputStream DataOut = new DataOutputStream(out)) {

            double x = DataIn.readDouble();
            double result = function(x);
            DataOut.writeDouble(result);

            DataOut.flush();
            System.out.println("Done.");
        } catch (IOException e) {
            System.err.println("Could not write or read the data from server");
        }
    }
}
