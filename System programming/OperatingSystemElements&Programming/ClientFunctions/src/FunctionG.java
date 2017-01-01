import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by ignas on 31.12.2016.
 */
public class FunctionG {
    public static void main(String[] arg) {
        int serverPort = 54321;
        String address = "127.0.0.1";

        try {
            InetAddress ipAddress = InetAddress.getByName(address);
            System.out.println("IP address " + address + " and port " + serverPort + "was opened");
            Socket socket = new Socket(ipAddress, serverPort);
            System.out.println("Yes! I just got hold of the program.");

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            Double x = in.readDouble();
            Double result = G(x);


            System.out.println("Sending this line to the server...");
            out.writeDouble(result);
            out.flush();

            System.out.println("Done.");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double G(double x) {
        if (x < 0) {
            return 0;
        }
        else if (x < 2) {
            return 1;
        }
        double resultG = 1;
        for(int i = 2; i <= x; i++) {
            resultG *= i;
        }
        return resultG;
    }
}
