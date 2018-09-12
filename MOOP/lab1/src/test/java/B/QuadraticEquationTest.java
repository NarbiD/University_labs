package B;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QuadraticEquationTest {

    private List<QuadraticEquation> equations;

    @Before
    public void before() {
        equations = new ArrayList<>();
        equations.add(new QuadraticEquation(2, 5, 2));
        equations.add(new QuadraticEquation(1, 2, 1));
        equations.add(new QuadraticEquation(1, 2, 3));
    }

    @Test
    public void findRoots() {
        equations.forEach(QuadraticEquation::printMinAndMaxRoots);
    }

    @Test
    public void findExtrem() {
        equations.forEach(QuadraticEquation::extremum);
    }

    @Test
    public void findIntervals() {
        equations.forEach(QuadraticEquation::intervals);
    }

}
