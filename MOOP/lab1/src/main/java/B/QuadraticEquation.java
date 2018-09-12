package B;

import java.util.*;

import static java.lang.StrictMath.max;
import static java.lang.StrictMath.min;
import static java.lang.StrictMath.sqrt;

public class QuadraticEquation {
    private double a, b, c;

    public QuadraticEquation(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Set<Double> solve() {
        Set<Double> roots = new HashSet<>();
        double d = b*b-4*a*c;
        if (d >= 0) {
            roots.add((-b-sqrt(d))/(2*a));
            roots.add((-b+sqrt(d))/(2*a));
        }
        return roots;
    }

    public Point<Double> extremum() {
        double x = -b/(2*a);
        double y = a*x*x + b*x + c;
        return new Point<>(x, y);
    }

    public String intervals() {
        Double extremumX = extremum().x;
        if (a > 0) {
            return "Функция спадает на промежутке (-бесконечность; " + extremumX + ") " +
                    "и возрастает на (" + extremumX + "; бесконечность)";
        } else {
            return "Функция возрастает на промежутке (-бесконечность; " + extremumX + ") " +
                    "и спадает на (" + extremumX + "; бесконечность)";
        }
    }

    public void printMinAndMaxRoots() {
        Set<Double> roots = solve();
        System.out.print("В уравнении ");
        if (roots.size() == 0) {
            System.out.println("корней нет");
        } else if (roots.size() == 1) {
            System.out.println("корень только один. Он равен + " + roots.toArray()[0]);
        } else {
            ArrayList<Double> rootsArray = new ArrayList<>(roots);
            System.out.print("наименьший корень=" + min(rootsArray.get(0), rootsArray.get(1)) + ", ");
            System.out.println("наибольший корень=" + max(rootsArray.get(0), rootsArray.get(1)));
        }
        }

    public static void main(String[] args) {
        List<QuadraticEquation> equations = new ArrayList<>();
        equations.add(new QuadraticEquation(2, 5, 2));
        equations.add(new QuadraticEquation(1, 2, 1));
        equations.add(new QuadraticEquation(1, 2, 3));
        equations.forEach(QuadraticEquation::printMinAndMaxRoots);
    }

    private class Point<T> {
        T x, y;

        Point(T x, T y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "x=" + x.toString() +
                    " y=" + y.toString() + "\n";
        }
    }
}
