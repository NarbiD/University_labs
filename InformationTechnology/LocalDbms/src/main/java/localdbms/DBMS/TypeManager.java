package localdbms.DBMS;

import localdbms.DataType;

import java.util.regex.Pattern;

public class TypeManager {

    public static boolean instanceOf(Object object, DataType dataType) {
        switch (dataType) {
            case INTEGER:
            case INTEGER_INVL:
                return object instanceof Integer;
            case CHAR:
                return Pattern.matches(".?", object.toString());
            case REAL:
                return object instanceof Double || object instanceof Integer;
            default:
                return false;
        }
    }

    public static boolean instanceOf(Object object, DataType dataType, IntegerInvlConstraint constraint) {
        if (dataType == DataType.INTEGER_INVL) {
            return object instanceof Integer && constraint.isValueValid((Integer) object);
        } else {
            return instanceOf(object, dataType);
        }
    }

    public static Object parseObjectByType(String object, DataType dataType) throws NumberFormatException {
        switch (dataType) {
            case INTEGER:
                return Integer.valueOf(object);
            case INTEGER_INVL:
                return Integer.valueOf(object);
            case CHAR:
                return Character.toString(object.charAt(0));
            case REAL:
                return Double.valueOf(object);
            default:
                throw new NumberFormatException("Unknown data format");
        }
    }
}
