package A;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Parking {
    private List<AtomicBoolean> places;

    public Parking(int amount) {
        places = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            places.add(new AtomicBoolean(false));
        }
    }

    public List<AtomicBoolean> getPlaces() {
        return places;
    }

    public void park(Car c) {
        new Thread(new Parker(this)).start();
    }

}
