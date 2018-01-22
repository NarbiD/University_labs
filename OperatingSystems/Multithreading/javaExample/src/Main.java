import java.util.InputMismatchException;
import java.util.Scanner;

import static java.lang.Thread.sleep;

public class Main {
    private final static int DEFAULT_THREAD_NUM = 50_000;
    private final static int DEFAULT_SLEEP_TIME = 20_000;

    public static void main(String[] args) throws InterruptedException {

        int threadNum = getThreadNum();
        System.out.println("Started.");

        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(() -> {
                try {
                    sleep(DEFAULT_SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        }
        System.out.println("All threads have been launched.");
    }

    private static int getThreadNum() {
        int threadNum;

        System.out.println("Enter number off threads: ");
        Scanner scanner = new Scanner(System.in);

        try {
            threadNum = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.err.println("Parameter must be an integer.");
            System.err.println("The number of threads will be " + DEFAULT_THREAD_NUM + ".");
            threadNum = DEFAULT_THREAD_NUM;
        }

        return threadNum;
    }
}
