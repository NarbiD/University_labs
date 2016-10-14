package Nonlinear_equations;

import java.lang.*;

/**
 * Created by NarbiD on 14.10.2016.
 */

public class Method {

    private final double eps = 0.0000001;

    private double equation(double x) {
        return x*x*x - 3*x*x - 17*x + 22;
    }

    public double Dichotomy(double left, double right) {
        System.out.println("Метод дихотомії(бінарний пошук):");

        if (Math.signum(equation(left)) == Math.signum(equation(right))) {
            System.out.println("Немає розв'язків");
            return Integer.MIN_VALUE;
        }

        double mid = left + (right - left) / 2;
        for(int i = 0; Math.abs(right - left) > eps; ++i) {
            System.out.println("Ітерація " + i);
            if (equation(mid) > 0) {
                right = mid;
            } else {
                left = mid;
            }
            mid = left + (right - left) / 2;
        }
        System.out.println(mid);
        return mid;
    }
}
