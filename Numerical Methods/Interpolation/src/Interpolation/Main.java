package Interpolation;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Double> x = new ArrayList<>();
        ArrayList<Double> y = new ArrayList<>();
        x.add(1.0);
        x.add(2.0);
        x.add(3.0);
        x.add(5.0);
        y.add(1.0);
        y.add(4.0);
        y.add(9.0);
        y.add(25.0);
        double result = Interpolation.Method.LaGrang(2.5,x,y);
        System.out.println(result);
    }
}
