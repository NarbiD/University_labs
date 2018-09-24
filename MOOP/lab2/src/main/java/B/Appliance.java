package B;

public abstract class Appliance {
    double power;
    boolean pluggedIn;
    String producer;
    String Model;
    Size size;

    public enum Size {
        XS, S, M, L, XL, UNKNOWN
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Appliance appliance = (Appliance) o;

        if (Double.compare(appliance.power, power) != 0) return false;
        if (!producer.equals(appliance.producer)) return false;
        if (!Model.equals(appliance.Model)) return false;
        return size == appliance.size;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(power);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + producer.hashCode();
        result = 31 * result + Model.hashCode();
        result = 31 * result + size.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Appliance{" +
                "power=" + power +
                ", pluggedIn=" + pluggedIn +
                ", producer='" + producer + '\'' +
                ", Model='" + Model + '\'' +
                ", size=" + size +
                '}';
    }

    public Appliance(double power, String producer, String model, Size size) {

        this.power = power;
        this.producer = producer;
        Model = model;
        this.size = size;
    }

    public void setPluggedIn(boolean pluggedIn) {
        this.pluggedIn = pluggedIn;
    }

    public double getPower() {
        return power;
    }

    public boolean isPluggedIn() {
        return pluggedIn;
    }

    public String getProducer() {
        return producer;
    }

    public String getModel() {
        return Model;
    }

    public Size getSize() {
        return size;
    }


}
