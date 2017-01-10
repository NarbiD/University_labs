package MultiThreadServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ignas on 28.12.2016.
 */

public class FlowController implements Runnable {

    private final int PORT;

    FlowController(final int _PORT) {
        PORT = _PORT;
    }

    public void run() {
        try (ServerSocket ss = new ServerSocket(PORT);
             Socket socket = ss.accept();
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();
             DataInputStream DataIn = new DataInputStream(in);
             DataOutputStream DataOut = new DataOutputStream(out)) {

            DataOut.writeDouble(DataHandler.getX());
            DataHandler.interimResults.add(DataIn.readDouble());

        } catch (IOException e) {
            System.err.println("Could not write or read the data from client");
        }
    }

}
