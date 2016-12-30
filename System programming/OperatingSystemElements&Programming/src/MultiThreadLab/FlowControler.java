package MultiThreadLab;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ignas on 28.12.2016.
 */
public class FlowControler implements Runnable {

    private final int PORT;
    public Double result;

    FlowControler(final int _PORT) {
        PORT = _PORT;
        result = null;
    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(PORT);
            Socket socket1 = ss.accept();

            InputStream in = socket1.getInputStream();
            OutputStream out = socket1.getOutputStream();

            DataInputStream DataIn = new DataInputStream(in);
            DataOutputStream DataOut = new DataOutputStream(out);

            DataOut.writeDouble(DataHandler.getX());
            DataHandler.interimResults.add(DataIn.readDouble());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
