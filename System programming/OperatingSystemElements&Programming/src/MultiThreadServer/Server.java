package MultiThreadServer;

import java.util.ArrayList;

/**
 * Created by ignas on 29.12.2016.
 */
public class Server {
    private static Server instance = null;
    public static final int TIME_TO_ASK = 10_000;

    private ArrayList<Integer> ports = new ArrayList<>(MainServer.NUMBER_OF_FUNCTION);
    private ArrayList<Thread> functions = new ArrayList<>(MainServer.NUMBER_OF_FUNCTION);

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    private void initPorts() {
        ports.add(12345);
        ports.add(54321);
    }

    private Server() {
        initPorts();
        for (int i = 0; i < MainServer.NUMBER_OF_FUNCTION; i++) {
            functions.add(new Thread(new FlowController(ports.get(i))));
        }
    }

    public void run() {
        System.out.println("Введіть X: ");
        DataHandler.setX(DataHandler.readVariable());

        functions.forEach(Thread::start);

        System.out.println("Очікуються клієнти...");
        System.out.println("Після запуску клієнтів натисніть Enter.");
        DataHandler.readVariable();

        DataHandler.getResult(functions);
    }
}
