package ClientFunctions;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ignas on 31.12.2016.
 */
public class FunctionF {
    public static void main(String[] arg) {
        int serverPort = 12345;
        String address = "127.0.0.1";

        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            System.out.println("IP address " + address + " and port " + serverPort + "was opened");
            Socket socket = new Socket(ipAddress, serverPort);
            System.out.println("Yes! I just got hold of the program.");

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            DataInputStream DataIn = new DataInputStream(in);
            DataOutputStream DataOut = new DataOutputStream(out);

            Double x = DataIn.readDouble();
            Double result = F(x);


            System.out.println("Sending this line to the server...");
            DataOut.writeDouble(result);
            out.flush();

            System.out.println("Done.");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double F(double x) {
        int a;
        while (true) {
            a = 5;
            if(a==6) break;
        }
        return a+1;

    }
}