package MultiThreadServer;

/**
 * Created by ignas on 27.12.2016.
 */
public class MainServer {
    public static final int NUMBER_OF_FUNCTION = 2;

    public static void main(String[] args) {

        Server mainServer = Server.getInstance();
        mainServer.run();
        DataHandler.printResult();
    }
}
