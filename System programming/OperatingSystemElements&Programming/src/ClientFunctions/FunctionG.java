package ClientFunctions;

/**
 * Created by ignas on 31.12.2016.
 */

public class FunctionG extends Client {

    private FunctionG() {
        super();
        serverPort = 54321;
    }

    public static void main(String[] arg) {

        FunctionG clientG = new FunctionG();

        if (clientG.connectToServer()) {
            clientG.dataProcessing();
        }

        System.out.println("<<<END OF WORK>>>");
    }

    protected double function(double x) {
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
