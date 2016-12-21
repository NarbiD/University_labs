package Nonlinear_equations;

/**
 * Created by NarbiD on 14.10.2016.
 */

public class Method {

    private static final double eps = 0.0000001;

    private static double equation(double x) {
        return x*x*x - 3*x*x - 17*x + 22;
    }
    private static double derivative(double x) {
        return 3*x*x - 6*x - 17;
    }

    public static double Dichotomy(double left, double right) {
        System.out.println("Метод дихотомії(бінарний пошук):");

        if (Math.signum(equation(left)) == Math.signum(equation(right))) {
            System.out.println("Немає розв'язків");
        }

        double mid = (left + right) / 2.0;
        for(int i = 1; Math.abs(right - left) > eps; ++i) {
            System.out.println("Ітерація " + i + ", " + mid);
            if (equation(mid)*equation(left) < 0.0) {
                right = mid;
            } else {
                left = mid;
            }
            mid = (left + right) / 2.0;
        }
        System.out.println(mid);
        return mid;
    }

    public static double FixedPointIteration(double left, double right) {
        System.out.println("Метод простої ітерації:");
        double x1 = (left + right) / 2.0;
        double lambda = 0.035 / Math.signum(derivative(x1));
        if (Math.abs(1.0 - lambda * derivative(x1)) >= 1) {
            System.out.println("Немає розв'язків");
        }
        double x2;
        x2 = x1;
        x1 = x2 - 2.0 * eps;
        for(int i = 1; Math.abs(x1 - x2) > eps; ++i) {
            x1 = x2;
            System.out.println("Ітерація " + (i) + ", " + x1);
            x2 -= lambda * equation(x1);
        }
        System.out.println(x2);
        return x2;
    }

    public static double Newton(double left, double right) {
        System.out.println("Метод Ньютона:");
        double x1 = (left + right) / 2.0;
        double lambda = 1.0 / derivative(x1);
        if (Math.abs(1.0 - lambda * derivative(x1)) >= 1) {
            System.out.println("Немає розв'язків");
        }
        double x2;
        x2 = x1;
        x1 = x2 - 2.0 * eps;
        for(int i = 1; Math.abs(x1 - x2) > eps; ++i) {
            x1 = x2;
            System.out.println("Ітерація " + (i) + ", " + x1);
            lambda = 1.0 / derivative(x2);
            x2 -= lambda * equation(x1);
        }
        System.out.println(x2);
        return x2;
    }
}
