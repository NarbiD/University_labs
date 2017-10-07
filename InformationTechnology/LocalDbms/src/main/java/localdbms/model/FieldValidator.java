package localdbms.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class FieldValidator {
    static boolean isTextFieldValid(String field, String pattern) {
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(field);
        return matcher.matches();
    }

    static boolean isNumberFieldValid(double value, double minValue, double maxValue) {
        return value >= minValue && value <= maxValue;
    }
}
