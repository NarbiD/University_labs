package B;

public class MicrowaveOven extends Appliance implements WarmUp {

    private double frequency;

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public MicrowaveOven(double power, String producer, String model, Size size) {
        super(power, producer, model, size);
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

        MicrowaveOven that = (MicrowaveOven) o;

        return Double.compare(that.frequency, frequency) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(frequency);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "MicrowaveOven{" +
                "frequency=" + frequency +
                ", power=" + power +
                ", pluggedIn=" + pluggedIn +
                ", producer='" + producer + '\'' +
                ", Model='" + Model + '\'' +
                ", size=" + size +
                '}';
    }
}
