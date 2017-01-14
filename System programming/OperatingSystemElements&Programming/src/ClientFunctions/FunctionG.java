package ClientFunctions;

import java.io.*;

/**
 * Created by ignas on 31.12.2016.
 */
public class FunctionG extends Client {

    public static void main(String[] arg) {
        serverPort = 54321;

        FunctionG client = new FunctionG();

        if (client.connectToServer()) {
            client.dataProcessing();
        }

        System.out.println("<<<END OF WORK>>>");
    }

    public double function(double x) {
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
