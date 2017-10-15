package localdbms.database;

import java.util.regex.Pattern;

class TypeChecker {
    private static final double DEFAULT_REAL_INTERVAL_MIN_VALUE = 10.0;
    private static final double DEFAULT_REAL_INTERVAL_MAX_VALUE = 100.0;

    static boolean instanceOf(Object object, DataType dataType) {
        switch (dataType) {
            case INTEGER:
                return object instanceof Integer;
            case CHAR:
                return Pattern.matches(".?", object.toString());
            case REAL:
                return object instanceof Double || object instanceof Integer;
            case REAL_INTERVAL:
                return (object instanceof Double || object instanceof Integer) && checkRealInterval((double)object);
            default:
                return false;
        }
    }

    private static boolean checkRealInterval(Double value) {
        return checkRealInterval(value, DEFAULT_REAL_INTERVAL_MIN_VALUE, DEFAULT_REAL_INTERVAL_MAX_VALUE);
    }

    private static boolean checkRealInterval(Double value, Double minValue, Double maxValue) {
        return minValue <= value && maxValue >= value;
    }
}
