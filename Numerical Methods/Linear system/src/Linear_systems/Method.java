package Linear_systems;

/**
 * Created by ignas on 17.12.2016.
 */
public class Method {
    public static final double EPS = 1e-9;
    // TODO: implements of methods
    public static Matrix Gauss(Matrix A, Matrix b) throws IllegalArgumentException {
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
                throw new IllegalArgumentException("Can't find not 0 element, bad matrix");
            }
            A.swapRows(i, maxRow);
            b.swapRows(i, maxRow);
            for (int j = i; j < NUM_ROWS; j++) {
                A.set(i, j, A.getElem(i, j) / maxElement);
            }
            b.set(i, 0, b.getElem(i, 0) / maxElement);
            for (int j = i + 1; j < NUM_ROWS; j++) {
                b.set(j, 0, b.getElem(j, 0) - A.getElem(j, i) * b.getElem(i, 0));
                for (int k = NUM_ROWS - 1; k >= i ; k--) {
                    A.set(j, k, A.getElem(j, k) - A.getElem(j, i) * A.getElem(i, k));
                }
            }
        }

        Matrix resultMatrix = new Matrix(NUM_ROWS, 1);
        for (int i = NUM_ROWS - 1; i >= 0; i--) {
            double sum = 0;
            for (int j = NUM_ROWS - 1; j > i; j--) {
                sum += A.getElem(i, j) * resultMatrix.getElem(j, 0);
            }
            resultMatrix.set(i, 0, (b.getElem(i, 0) - sum) / A.getElem(i, i));
        }
        return resultMatrix;
    }

}
