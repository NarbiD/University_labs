package ClientFunctions;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by NarbiD on 10.01.2017.
 */

abstract class Client implements Runnable {
    protected static final String localhost = "127.0.0.1";
    protected static Integer serverPort = null;
    protected static Socket socket = null;

    protected abstract double function(double x);
    protected double result;
    protected double x;

    protected Client (){
        x = Double.NaN;
        result = Double.NaN;
    }

        protected boolean connectToServer() {
        try {
            InetAddress ipAddress = InetAddress.getByName(localhost);
            socket = new Socket(ipAddress, serverPort);
            System.out.println("IP address " + ipAddress + " and port " + serverPort + "was opened");
        } catch (IOException e) {
            System.err.println("Could not create a connection!");
            return false;
        }
        return true;
    }

    protected void dataProcessing(){
        try (
             DataInputStream DataIn = new DataInputStream(socket.getInputStream());
             DataOutputStream DataOut = new DataOutputStream(socket.getOutputStream())) {

            x = DataIn.readDouble();
            this.run();

            while (Double.isNaN(result)) {
                DataOut.writeDouble(result);
                String answerFromServer = DataIn.readUTF();
                switch (answerFromServer) {
                    case "Stop":
                        System.out.println("<<<END OF WORK>>>");
                        System.exit(0);
                        break;
                    case "Pause":
                        this.wait();
                        break;
                    case "Resume":
                        this.notify();
                        break;
                    default:
                        break;
                }
            }
            DataOut.writeDouble(result);
            DataOut.flush();
            System.out.println("Done.");
        } catch (IOException e) {
            System.err.println("Could not write or read the data from server");
            System.exit(-1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        result = function(x);
    }
}
