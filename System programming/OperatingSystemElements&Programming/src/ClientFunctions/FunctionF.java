package ClientFunctions;

import java.io.*;

/**
 * Created by ignas on 31.12.2016.
 */
public class FunctionF extends Client{

    public static void main(String[] arg) {
        serverPort = 12345;

        FunctionF client = new FunctionF();
        if (client.connectToServer()) {
            client.dataProcessing();
        }

        System.out.println("<<<END OF WORK>>>");
    }

    public double function(double x) {
        int a;
        while (true) {
            a = 5;
            if(a==6) break;
        }
        return a+1;

    }
}