import java.util.InputMismatchException;
import java.util.Arrays;

/**
 * Created by ignas on 17.12.2016.
 */
class Matrix {
    private double[][] matrix; //matrix
    private int rows = 0;
    private int cols = 0;

    Matrix(){
        matrix = null;
    }

    void setSize(int rows,int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    void initMatrix() {
        matrix = new double[rows][cols];
    }

    Matrix(Matrix m) {
        rows = m.getNumberOfRows();
        cols = m.getNumberOfCols();
        matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            matrix[i] = Arrays.copyOf(m.matrix[i], cols);
        }
    }

    int getNumberOfRows() {
        return rows;
    }

    int getNumberOfCols() {
        return cols;
    }

    double getElem(int i, int j) {
        if (i < rows && j < cols) {
            return matrix[i][j];
        }
        else return Double.NaN;
    }

    void setElem (int i, int j, double value) {
        try {
            matrix[i][j] = value;
        }
        catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }

    void swapRows(int row1, int row2) {
        double[] tempRow = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = tempRow;
    }
}
