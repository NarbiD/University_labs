package C;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PolynomialTest {

    private List<Polynomial> polynomials;

    @Before
    public void makeListOfPolynomials() {
        polynomials = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            int n = (int) (Math.random() * 100);
            List<Complex> complexes = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                complexes.add(new Complex(Math.random()*100, Math.random()*100));
            }
            polynomials.add(new Polynomial(complexes));
        }
    }

    @Test
    public void sumOfPolynomials() {
        Polynomial sumOfPolynomials = new Polynomial();
        for (Polynomial p: polynomials) {
            sumOfPolynomials = sumOfPolynomials.add(p);
        }
        System.out.println(sumOfPolynomials.toString());

    }

    @Test
    public void myTest() {
        Polynomial p1 = new Polynomial(new Complex(1,21), new Complex(2, 22));
        Polynomial p2 = new Polynomial(new Complex(4,23), new Complex(3, 24));
        System.out.println(p1.add(p2).toString());
    }
}
