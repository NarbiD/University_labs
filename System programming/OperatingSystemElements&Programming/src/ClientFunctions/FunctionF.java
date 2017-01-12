package ClientFunctions;

/**
 * Created by ignas on 31.12.2016.
 */

public class FunctionF extends Client {

    private FunctionF() {
        super();
        serverPort = 12345;
    }

    public static void main(String[] arg) {

        FunctionF clientF = new FunctionF();

        if (clientF.connectToServer()) {
            clientF.dataProcessing();
        }

        System.out.println("<<<END OF WORK>>>");
    }

    protected double function(double x) {
        return 1+x;
    }
}