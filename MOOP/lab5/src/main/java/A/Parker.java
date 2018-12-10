package A;

import org.omg.PortableServer.THREAD_POLICY_ID;

public class Parker implements Runnable {

    private Parking parking;
    private static final int PAUSE = 200; // in millis

    public Parker(Parking parking) {
        this.parking = parking;
    }

    public int park() {
        for (int i=0; i < parking.getPlaces().size(); i++) {
            System.out.println("Проверяется место №" + i + "." + " ("+ Thread.currentThread() + ")");
            if(parking.getPlaces().get(i).compareAndSet(false, true)) {
                new Thread(new Unparker(parking, i)).start();
                return i;
            }
        }
        return -1;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 4; i++) {
                int parkPlace = park();
                if (parkPlace >= 0) {
                    System.out.println("Машина припаркована на место №" + parkPlace + "."  + " ("+ Thread.currentThread() + ")");
                    return;
                } else {
                    Thread.sleep(PAUSE);
                    System.out.println("Не удалось припарковать машину. Следующая попытка через " + PAUSE + "мс."  + " ("+ Thread.currentThread() + ")");
                }
            }
            System.out.println("Не удалось припарковать машину. Следует отправиться на другую парковку."  + " ("+ Thread.currentThread() + ")");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}