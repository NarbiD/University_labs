public class Main {
    public static void main(String[] args) {
        for (int i = 0; i < 500; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    test();
                }

                double test() {
                    double var;
                    while (true) {
                        var = Math.sqrt(1);
                        if (var == 2) {
                            break;
                        }
                    }
                    return var;
                }
            });
            thread.start();
        }
    }
}
