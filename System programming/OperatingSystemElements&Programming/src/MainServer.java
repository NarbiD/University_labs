import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ignas on 27.12.2016.
 */
public class MainServer {
    private static final int timeToAsk = 1_000;
    private static int needToContinue = -1;

    private static volatile Double variableX = 0.0;
    private static volatile Double resultF = null;
    private static volatile Double resultG = null;
    private static volatile Double resultTotal = null;


    public static void main(String[] args) {
        final int PORT_1 = 12345;
        final int PORT_2 = 54321;

        Runnable functionF = new F_Thread();
        Runnable functionG = new G_Thread();

        Thread threadCalcF = new Thread(functionF);
        Thread threadCalcG = new Thread(functionG);

        try {
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Waiting for a client...");
            System.out.println("Enter x");
            String a = keyboard.readLine();
            variableX = Double.parseDouble(a);
            threadCalcF.start();
            threadCalcG.start();
            System.out.println("Start clients threads and press enter");
            keyboard.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long timeStart = System.currentTimeMillis();
        do {
            resultTotal = getResult();
            if (resultTotal != null) {
                break;
            }
            if (!shouldContinue(timeStart)) {
                threadCalcF.stop();
                threadCalcG.stop();
                break;
            } else {
                if (System.currentTimeMillis() - timeStart > timeToAsk) {
                    timeStart = System.currentTimeMillis();
                }
            }
        } while (threadCalcF.isAlive() || threadCalcG.isAlive());

        printResult(resultTotal);
        System.out.println();
    }

    private static boolean shouldContinue(long timeBefore) {
        if (needToContinue != 3
                && System.currentTimeMillis() - timeBefore > timeToAsk) {
            try {
                BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Увага!");
                System.out.println("Обчислення тривають більше " + timeToAsk / 1000 + " секунд");
                System.out.println("Якщо ви бажаєте продовжувати, натисніть 1");
                System.out.println("Якщо ви бажаєте закінчити, натисніть 2");
                System.out.println("Якщо ви бажаєте продовжувати до кінця (більше не питати), натисніть 3");
                needToContinue = keyboard.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (needToContinue == 2) {
            return false;
        }
        else {
            return true;
        }
    }

    private static Double getResult() {
        if (resultF != null && resultF.equals(0)) {
            return 0.0;
        }

        if (resultG != null && resultG.equals(0)) {
            return 0.0;
        }

        if (resultF == null || resultG == null) {
            return null;
        }

        return resultF * resultG;
    }

    private static void printResult(Double result) {
        if (result == null) {
            System.out.println("Обчислення не були виконані");
        } else {
            System.out.println("Результат: " + resultTotal);
        }
    }

    public static Double getX() {
        return variableX;
    }

    public static void setResultF(double result) {
        resultF = result;
    }

    public static void setResultG(double result) {
        resultG = result;
    }
}
