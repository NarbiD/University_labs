package MultiThreadServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ignas on 30.12.2016.
 */

public class DataHandler {
    private static volatile Double variableX = null;
    public static volatile ArrayList<Double> interimResults = new ArrayList<>(2);
    private static Double ultimateResult = null;
    private static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

    public static void getResult(ArrayList<Thread> functions) {
        long _timeStart = System.currentTimeMillis();
        byte needToContinue = 0;
        boolean allFunctionsAreDied = false;
        while (!allFunctionsAreDied) {
            if (resultIsReady()) {
                ultimateResult  = multInterimResults();
                break;
            }
            else if (!shouldContinue(_timeStart, needToContinue, functions)) {
                FlowController.dontContinue();
                functions.forEach(Thread::interrupt); //.stop
                break;
            }
            else if (System.currentTimeMillis() - _timeStart > Server.TIME_TO_ASK) {
                _timeStart = System.currentTimeMillis();
            }
            allFunctionsAreDied = true;
            for (Thread func : functions) {
                if (func.isAlive()) {
                    allFunctionsAreDied = false;
                }
            }
        }
    }

    public static double readVariable() {
        double _x;
        try {
            _x  = Double.parseDouble(keyboard.readLine());
        } catch (IOException e) {
            System.err.println("Помилка вводу!");
            _x = Double.NaN;
        } catch (IllegalArgumentException e) {
            _x = Double.NaN;
        }
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

    private static double multInterimResults() {
        double _resultOfMult = 1.0;
        for (Double result : interimResults) {
            _resultOfMult *= result;
        }
        return _resultOfMult;
    }

    private static boolean shouldContinue(long timeBefore, byte needToContinue, ArrayList<Thread> functions) {
        if (needToContinue != 3 && System.currentTimeMillis() - timeBefore > Server.TIME_TO_ASK) {

            FlowController.pause();
            functions.forEach(Thread::suspend);

            System.out.println("Увага!");
            System.out.println("Обчислення тривають більше " + Server.TIME_TO_ASK / 1000 + " секунд.");
            System.out.println("Якщо ви бажаєте продовжувати (варіант по замовчуванню), натисніть 1.");
            System.out.println("Якщо ви бажаєте закінчити, натисніть 2.");
            System.out.println("Якщо ви бажаєте продовжувати до кінця (більше не питати), натисніть 3.");
            System.out.println("Якщо ви бажаєте побачити проміжні результати і продовжити, натисніть 4.");

            double inputFromKeyboard = readVariable();
            if (Double.isNaN(inputFromKeyboard)) {
                System.err.println("Некоректний ввід! Обчислення будуть припинені.");
                needToContinue = 2;
            }
            else {
                needToContinue = (byte)inputFromKeyboard;
            }
        }
        switch (needToContinue) {
            case 2:
                return false;
            case 4:
                printInterimResults();
            case 1:
            case 3:
                FlowController.resume();
                functions.forEach(Thread::resume);
            default:
                return true;
        }
    }

    public static void printResult() {
        if (ultimateResult == null) {
            System.out.println("Не всі обчислення були виконані.");
            printInterimResults();
        } else {
            System.out.println("Результат: " + ultimateResult + ".");
        }
    }

    public static void printInterimResults() {
        DataHandler.interimResults.forEach(System.out::println);
    }

    public static double getX() {
        return variableX;
    }

    public static void setX(double x) throws IllegalArgumentException{
        if (Double.isNaN(x) || Double.isInfinite(x)) {
            throw new IllegalArgumentException();
        }
        else
            variableX = x;
    }

}
