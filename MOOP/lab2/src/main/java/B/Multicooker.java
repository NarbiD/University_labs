package B;

public class Multicooker extends Appliance implements WarmUp {



    private final double volume;

    public Multicooker(double power, String producer, String model, Size size, double volume) {
        super(power, producer, model, size);
        this.volume = volume;
    }

    public void fry() {
        // code for frying
    }

    public void stopFry() {
        // code for stop fry
    }

    public void fry(int seconds) {
        // code for frying for a few seconds
    }

    @Override
    public void warm() {
        // code for warming
    }

    @Override
    public void stopWarm() {
        // code for stop warm
    }

    @Override
    public void warm(int seconds) {
        // code for warming for a few seconds
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Multicooker that = (Multicooker) o;

        return Double.compare(that.volume, volume) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(volume);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Multicooker{" +
                "volume=" + volume +
                ", power=" + power +
                ", pluggedIn=" + pluggedIn +
                ", producer='" + producer + '\'' +
                ", Model='" + Model + '\'' +
                ", size=" + size +
                '}';
    }

    public double getVolume() {
        return volume;
    }
}
