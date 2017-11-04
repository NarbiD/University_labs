package localdbms.DBMS;

public class IntegerInvlConstraint {
    private Integer minValue;
    private Integer maxValue;

    public IntegerInvlConstraint() {
        this.minValue = Integer.MIN_VALUE;
        this.maxValue = Integer.MAX_VALUE;
    }

    public IntegerInvlConstraint(Integer minValue, Integer maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public boolean isValueValid(Integer value) {
        return value.compareTo(minValue) >= 0 && value.compareTo(maxValue) <= 0;
    }

    public boolean isDefined() {
        return !(Double.MAX_VALUE == maxValue && Double.MIN_VALUE == minValue);
    }

}
