import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 100_000; i++) {
            int finalI = i;
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        sleep(30_000);
//                        System.out.println(finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
}
