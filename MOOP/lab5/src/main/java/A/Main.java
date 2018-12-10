package A;

public class Main {
    public static void main(String[] args) {
        Parking parking = new Parking(2);
        for (int i = 0; i < 4; i++) {
            parking.park(new Car());
        }

    }

}
