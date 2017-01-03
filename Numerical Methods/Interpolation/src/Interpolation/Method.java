package Interpolation;

import java.util.ArrayList;

/**
 * Created by ignas on 22.12.2016.
 */
public class Method {
    public static double LaGrang(double arg, ArrayList<Double> x, ArrayList<Double> y) {
        double result = 0, l = 1;
        for (int i = 0; i < x.size(); i++, l = 1) {
            for (int j = 0; j < y.size(); j++) {
                if (j != i) {
                    l *= (arg - x.get(j)) / (x.get(i) - x.get(j));
                }
            }
            result += l * y.get(i);
        }
        return result;
    }

}
