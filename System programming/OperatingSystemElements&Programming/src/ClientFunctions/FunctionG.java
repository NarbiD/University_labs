package ClientFunctions;

import java.io.*;

/**
 * Created by ignas on 31.12.2016.
 */
public class FunctionG extends Client {

    public static void main(String[] arg) {
        serverPort = 54321;

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
            double result = G(x);
            DataOut.writeDouble(result);

            System.out.println("Done.");
        } catch (IOException e) {
            System.err.println("Could not write or read the data from server");
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
