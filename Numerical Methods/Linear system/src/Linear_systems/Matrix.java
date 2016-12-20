package Linear_Systems;

import java.util.InputMismatchException;
import java.util.Arrays;

/**
 * Created by ignas on 17.12.2016.
 */
public class Matrix {
    private double[][] matrix; //matrix
    private int rows = 0;
    private int cols = 0;

    public Matrix(){
        matrix = null;
    }

    public void setSize(int rows,int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public void initMatrix() {
        matrix = new double[rows][cols];
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];
    }

    public Matrix(Matrix m) {
        rows = m.getNumberOfRows();
        cols = m.getNumberOfCols();
        matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            matrix[i] = Arrays.copyOf(m.matrix[i], cols);
        }
    }

    public int getNumberOfRows() {
        return rows;
    }

    public int getNumberOfCols() {
        return cols;
    }

    public double getElem(int i, int j) {
        return matrix[i][j];
        //TODO: throw exception
    }



    public void setElem (int i, int j, double value) {
        try {
            matrix[i][j] = value;
        }
        catch (InputMismatchException e) {
            e.printStackTrace();
        }
        //TODO: throw exception
    }

    public void swapRows(int row1, int row2) {
        double[] tempRow = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = tempRow;
    }

    public void printMatrix() {
        for (int i = 0; i < rows; i++) {
            System.out.print(matrix[i][0]);
            for (int j = 1; j < cols; j++) {
                System.out.print(" " + matrix[i][j]);
            }
            System.out.println();
        }
    }

    public Matrix addColumn(Matrix addingMatrix) {
        Matrix newMatrix = new Matrix(rows, cols + addingMatrix.getNumberOfCols());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMatrix.setElem(i, j, matrix[i][j]);
            }
        }
        for (int i = 0; i < addingMatrix.getNumberOfRows(); i++) {
            for (int j = 0; j < addingMatrix.getNumberOfCols(); j++) {
                newMatrix.setElem(i, cols + j, addingMatrix.getElem(i, j));
            }
        }
        return newMatrix;
    }

    public Matrix getTransposed() {
        Matrix transposedMatrix = new Matrix(cols, rows);
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposedMatrix.setElem(i, j, matrix[j][i]);
            }
        }
        return transposedMatrix;
    }


}
