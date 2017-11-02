package localdbms.DBMS;

import localdbms.DataType;

import java.util.regex.Pattern;

public class TypeManager {

    public static boolean instanceOf(Object object, DataType dataType) {
        switch (dataType) {
            case INTEGER:
                return object instanceof Integer;
            case CHAR:
                return Pattern.matches(".?", object.toString());
            case REAL:
                return object instanceof Double || object instanceof Integer;
            default:
                return false;
        }
    }

    public static Object parseObjectByType(String object, DataType dataType) throws NumberFormatException {
        switch (dataType) {
            case INTEGER:
                return Integer.valueOf(object);
            case CHAR:
                return object.charAt(0);
            case REAL:
                return Double.valueOf(object);
            default:
                throw new NumberFormatException("Unknown data format");
        }
    }
}
