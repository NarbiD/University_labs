package MultiThreadLab;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by ignas on 30.12.2016.
 */

public class DataHandler {
    private static volatile Double variableX = null;
    public static volatile ArrayList<Double> interimResults = new ArrayList<>(2);
    private static Double result = null;

    public static void getResult(Thread f, Thread g) {
        long _timeStart = System.currentTimeMillis();
        byte needToContinue = 0;
        do {
            if (resultIsReady()) {
                result = multInterimResults();
                break;
            }
            else if (!shouldContinue(_timeStart, needToContinue)) {
                f.interrupt(); // .stop()
                g.interrupt(); // .stop()
                break;
            }
            else if (System.currentTimeMillis() - _timeStart > Server.TIME_TO_ASK) {
                _timeStart = System.currentTimeMillis();
            }
        } while (f.isAlive() || g.isAlive());
    }

    public static double readVar() {
        Scanner scanner = new Scanner(System.in);
        Double _x = scanner.nextDouble();
        scanner.close();
        return _x;
    }

    private static boolean resultIsReady() {
        if (interimResults.size() < MainServer.NUMBER_OF_FUNCTION) {
            return false;
        }
        for (Double result: interimResults) {
            if(result == null) {
                return false;
            }
        }
        return true;
    }

    private static Double multInterimResults() {
        Double _resultOfMult = 1.0;
        for (Double result : interimResults) {
            _resultOfMult *= result;
        }
        return _resultOfMult;
    }

    private static boolean shouldContinue(long timeBefore, byte needToContinue) {
        if (needToContinue != 3 && System.currentTimeMillis() - timeBefore > Server.TIME_TO_ASK) {
            System.out.println("Увага!");
            System.out.println("Обчислення тривають більше " + Server.TIME_TO_ASK / 1000 + " секунд.");
            System.out.println("Якщо ви бажаєте продовжувати, натисніть 1.");
            System.out.println("Якщо ви бажаєте закінчити, натисніть 2.");
            System.out.println("Якщо ви бажаєте продовжувати до кінця (більше не питати), натисніть 3.");
            needToContinue = (byte)readVar();
        }
        return needToContinue != 2;
    }

    public static void printResult() {
        if (result == null) {
            System.out.println("Обчислення не були виконані.");
        } else {
            System.out.println("Результат: " + result + ".");
        }
    }

    public static Double getX() {
        return variableX;
    }

    public static void setX(Double x) {
        variableX = x;
    }

}
