package Nonlinear_equations;

import java.lang.*;


/**
 * Created by NarbiD on 14.10.2016.
 */
public class Method {

    final double eps = 0.0000001;

    public double equation(double _x) {
        double res = 0;
        // TODO: Write equation here
        return res;
    }

    private double Dihot(double left, double right) {
        System.out.println("Метод дихотомії(бінарний пошук):");

        double mid;

        // TODO: check equation

        for(int i = 0; Math.abs(right - left) > eps; ++i) {
            mid = left + (right - left) / 2;
            if (equation(mid) > 0) {
                right = mid;
            } else {
                left = mid;
            }
        }
        mid = left + (right - left) / 2;
        System.out.println(mid);
        return mid;
    }
}
