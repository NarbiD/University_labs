package MultiThreadLab;

/**
 * Created by ignas on 29.12.2016.
 */
public class Server {
    private static Server instance = null;
    public static final int TIME_TO_ASK = 10_000;

    private final int PORT_1 = 12345;
    private final int PORT_2 = 54321;
    private Thread functionF;
    private Thread functionG;

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    private Server() {

        FlowControler FlowControlerF = new FlowControler(PORT_1);
        FlowControler FlowControlerG = new FlowControler(PORT_2);

        functionF = new Thread(FlowControlerF);
        functionG = new Thread(FlowControlerG);
    }

    public void run() {
        System.out.println("Введіть X: ");
        DataHandler.setX(DataHandler.readVar());
        functionF.start();
        functionG.start();
        System.out.println("Очікуються клієнти...");
        System.out.println("Після запуску клієнтів натисніть будь-яку клавішу.");
        DataHandler.readVar();
        DataHandler.getResult(functionF, functionG);
    }
}
