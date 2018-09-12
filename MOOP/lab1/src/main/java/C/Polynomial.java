package C;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Polynomial {
    private List<Complex> coefficients;

    public Polynomial(Complex...coefficientArray) {
        this.coefficients = new ArrayList<>(Arrays.asList(coefficientArray));
    }

    public Polynomial(List<Complex> coefficients) {
        this.coefficients = coefficients;
    }

    public List<Complex> getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(List<Complex> coefficients) {
        this.coefficients = coefficients;
    }

    public Polynomial add(Polynomial polynomial) {
        Polynomial larger, smaller, result;

        if (this.coefficients.size() == polynomial.coefficients.size()) {
            larger = this;
            smaller = polynomial;
        } else {
            larger = this.coefficients.size() > polynomial.coefficients.size() ?
                    this : polynomial;
            smaller = this.coefficients.size() < polynomial.coefficients.size() ?
                    this : polynomial;
        }
        result = new Polynomial();

        for (int i = 0; i < larger.coefficients.size(); i++) {
            if (smaller.coefficients.size() - i - 1 >= 0) {
                Complex numFromLarger = larger.coefficients.get(larger.coefficients.size() - i - 1);
                Complex numFromSmaller = smaller.coefficients.get(smaller.coefficients.size() - i - 1);
                result.coefficients.add(numFromLarger.add(numFromSmaller));
            } else {
                result.coefficients.add(larger.coefficients.get(larger.coefficients.size() - i - 1));
            }
        }
        Collections.reverse(result.coefficients);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder polynomialString = new StringBuilder();
        int size = coefficients.size();

        for (int i = 0; i < size - 1; i++) {
            polynomialString.append(coefficients.get(i)).append("^").append(size - i).append(" + ");
        }
        polynomialString.append(this.coefficients.get(size-1)).append("^").append(size - 1);

        return polynomialString.toString();
    }
}
