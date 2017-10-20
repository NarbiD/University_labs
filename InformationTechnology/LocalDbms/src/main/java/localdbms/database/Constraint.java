package localdbms.database;

public abstract class Constraint<T extends Comparable<T>> {
    protected T minValue;
    protected T maxValue;

    public Constraint(T minValue, T maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public T getMinValue() {
        return minValue;
    }

    public T getMaxValue() {
        return maxValue;
    }

    public boolean isValueValid(T value) {
        return value.compareTo(minValue) >= 0 && value.compareTo(maxValue) <= 0;
    }
}
