package ClientFunctions;

import java.io.*;

/**
 * Created by ignas on 31.12.2016.
 */
public class FunctionF extends Client{

    public static void main(String[] arg) {
        serverPort = 12345;

        if (connectToServer()) {
            dataProcessing();
        }

        System.out.println("<<<END OF WORK>>>");
    }

    protected static void dataProcessing() {
        try (InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();
             DataInputStream DataIn = new DataInputStream(in);
             DataOutputStream DataOut = new DataOutputStream(out)) {

            double x = DataIn.readDouble();
            double result = F(x);
            DataOut.writeDouble(result);

            System.out.println("Done.");
        } catch (IOException e) {
            System.err.println("Could not write or read the data from server");
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