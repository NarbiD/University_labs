import java.util.ArrayList;

/**
 * Created by ignas on 17.12.2016.
 */
class Solver {
    private static final double EPS = 1e-9;

    static ArrayList<Double> Gauss(Matrix A, Matrix b) throws IllegalArgumentException {
        A = new Matrix(A);
        b = new Matrix(b);
        final int NUM_ROWS = A.getNumberOfRows();
        for (int i = 0; i < NUM_ROWS - 1; i++) {
            int maxRow = i;
            double maxElement = A.getElem(i, i);
            for (int j = i + 1; j < NUM_ROWS; j++) {
                if (Math.abs(A.getElem(j, i)) > Math.abs(maxElement)) {
                    maxElement = A.getElem(j, i);
                    maxRow = j;
                }
            }
            if (Math.abs(maxElement) < EPS) {
                throw new IllegalArgumentException("Не знайдено ненульвих елементів");
            }
            A.swapRows(i, maxRow);
            b.swapRows(i, maxRow);
            for (int j = i; j < NUM_ROWS; j++) {
                A.setElem(i, j, A.getElem(i, j) / maxElement);
            }
            b.setElem(i, 0, b.getElem(i, 0) / maxElement);
            for (int j = i + 1; j < NUM_ROWS; j++) {
                b.setElem(j, 0, b.getElem(j, 0) - A.getElem(j, i) * b.getElem(i, 0));
                for (int k = NUM_ROWS - 1; k >= i; k--) {
                    A.setElem(j, k, A.getElem(j, k) - A.getElem(j, i) * A.getElem(i, k));
                }
            }
        }
        ArrayList<Double> result = new ArrayList<>();
        for (int i = 0; i < NUM_ROWS; i++) {
            result.add(0.0);
        }
        for (int i = NUM_ROWS - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = NUM_ROWS - 1; j > i; j--) {
                sum += A.getElem(i, j) * result.get(j);
            }
            result.set(i, (b.getElem(i, 0) - sum) / A.getElem(i, i));
        }

        return result;
    }

    static ArrayList<Double> Jacobi(Matrix A, Matrix b) {
        int size = A.getNumberOfRows();
        ArrayList<Double> previousValues = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            previousValues.add(0.0);
        }
        while (true) {
            ArrayList<Double> currentValues = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                currentValues.add(b.getElem(i, 0));
                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        currentValues.set(i, currentValues.get(i) - A.getElem(i, j) * previousValues.get(j));
                    }
                }
                currentValues.set(i, currentValues.get(i) / A.getElem(i, i));
            }
            double error = 0.0;
            for (int i = 0; i < size; i++) {
                error += Math.abs(currentValues.get(i) - previousValues.get(i));
            }
            if (error < EPS) {
                break;
            }
            previousValues = currentValues;
        }

        return previousValues;
    }

    static ArrayList<Double> Seidel(Matrix A, Matrix b) {
        int size = A.getNumberOfRows();

        ArrayList<Double> previousValues = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            previousValues.add(0.0);
        }
        while (true) {
            ArrayList<Double> currentValues = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                currentValues.add(b.getElem(i, 0));

                for (int j = 0; j < size; j++) {
                    if (j < i) {
                        currentValues.set(i, currentValues.get(i) - A.getElem(i, j) * currentValues.get(j));
                    }
                    else if (j > i) {
                        currentValues.set(i, currentValues.get(i) - A.getElem(i, j) * previousValues.get(j));
                    }
                }
                currentValues.set(i, currentValues.get(i) / A.getElem(i, i) );
            }
            double error = 0.0;
            for (int i = 0; i < size; i++) {
                error += Math.abs(currentValues.get(i) - previousValues.get(i));
            }
            if (error < EPS) {
                break;
            }
            previousValues = currentValues;
        }
        return previousValues;
    }
}