package B;

public class Fridge extends Appliance {
    private double volume;
    private double color;
    private double temperature;
    private boolean isFreezer;
    private final double minTemperature;

    public Fridge(double power, Size size, String producer, String model) {
        super(power, producer, model, size);
        this. minTemperature = 0.0;
    }

    public Fridge(double power, Size size, String producer, String model, double volume, double color, double minTemperature) {
        super(power, producer, model, size);
        this.volume = volume;
        this.color = color;
        this.minTemperature = minTemperature;
    }

    public Fridge(double power, Size size, String producer, String model, double volume, double color, double temperature, boolean freezer) {
        this(power, size, producer, model, volume, color, temperature);
        this.isFreezer = freezer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Fridge fridge = (Fridge) o;

        if (Double.compare(fridge.volume, volume) != 0) return false;
        if (Double.compare(fridge.color, color) != 0) return false;
        if (isFreezer != fridge.isFreezer) return false;
        return Double.compare(fridge.minTemperature, minTemperature) == 0;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(volume);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(color);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isFreezer ? 1 : 0);
        temp = Double.doubleToLongBits(minTemperature);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Fridge{" +
                "volume=" + volume +
                ", color=" + color +
                ", temperature=" + temperature +
                ", isFreezer=" + isFreezer +
                ", minTemperature=" + minTemperature +
                ", power=" + power +
                ", pluggedIn=" + pluggedIn +
                ", producer='" + producer + '\'' +
                ", Model='" + Model + '\'' +
                ", size=" + size +
                '}';
    }

    public void increaseTemperature(double grade) {
        this.temperature += grade;
    }

    public void lowerTemperature(double grade) {
        if (this.temperature - grade > minTemperature) {
            this.temperature -= grade;
        }
    }

    public double getVolume() {
        return volume;
    }

    public double getColor() {
        return color;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

}
