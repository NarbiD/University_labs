package A;

public class Unparker implements Runnable {

    private Parking parking;
    private int num;
    private int pause = 300; // in millis

    public Unparker(Parking parking, int place, int pause) {
        this(parking, place);
        this.pause = pause;
    }

    public Unparker(Parking parking, int place) {
        this.parking = parking;
        this.num = place;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(pause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        parking.getPlaces().get(num).compareAndSet(true, false);
        System.out.println("Место №" + num + " освобождено.");
    }
}
