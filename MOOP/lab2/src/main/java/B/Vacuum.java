package B;

public class Vacuum extends Appliance {

    private boolean bagisFull;
    private String color;

    public Vacuum(double power, String producer, String model, Size size, String color) {
        super(power, producer, model, size);
        this.color = color;
    }

    public void clean() {
        // code for start cleaning
    }

    public void stopClean() {
        // code for stop cleaning
    }

    public boolean isBagisFull() {
        return bagisFull;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Vacuum vacuum = (Vacuum) o;

        return color.equals(vacuum.color);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Vacuum{" +
                "bagisFull=" + bagisFull +
                ", color='" + color + '\'' +
                ", power=" + power +
                ", pluggedIn=" + pluggedIn +
                ", producer='" + producer + '\'' +
                ", Model='" + Model + '\'' +
                ", size=" + size +
                '}';
    }
}
