package MultiThreadServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ignas on 28.12.2016.
 */

public class FlowController implements Runnable {

    private final int PORT;
    private static boolean needToContinue;
    private static boolean needPause;
    private static boolean needResume;

    FlowController(final int _PORT) {
        PORT = _PORT;
        needToContinue = true;
        needPause = false;
        needResume = false;
    }

    public void run() {
        try (ServerSocket s = new ServerSocket(PORT);
                Socket serverSocket = s.accept();
                InputStream in = serverSocket.getInputStream();
                OutputStream out = serverSocket.getOutputStream();
                DataInputStream getFromClient = new DataInputStream(in);
                DataOutputStream sendToClient = new DataOutputStream(out)) {

            double _x = DataHandler.getX();
            double result = Double.NaN;
            sendToClient.writeDouble(_x);
            while (Double.isNaN(result)) {
                result = getFromClient.readDouble();
                if (!needToContinue){
                    sendToClient.writeChars("Stop");
                    break;
                }
                else if (needPause) {
                    sendToClient.writeChars("Pause");
                }
                else if (needResume) {
                    sendToClient.writeChars("Resume");
                }
                else {
                    sendToClient.writeChars("");
                }
            }
            if (DataHandler.interimResults.add(result)) {
                sendToClient.writeChars("Result_was_returned_to_server");
            }

            sendToClient.flush();
        } catch (IOException e) {
            System.err.println("Could not write or read the data from client");
            System.exit(-1);
        }
    }

    static void dontContinue() {
        needToContinue = false;
    }

    static void pause() {
        needPause = true;
        needResume = false;
    }

    static void resume() {
        needPause = false;
        needResume = true;
    }
}
