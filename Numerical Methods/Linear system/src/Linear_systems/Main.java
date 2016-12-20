package Linear_Systems;

import java.lang.String;


public class Main {

    public static void main(String[] args) {
        String fileName = "src\\q.txt";
        FileReader reader = new FileReader(fileName);
        Matrix A = new Matrix();
        Matrix b = new Matrix();
        reader.readMatrix(A, b);

        Matrix resultMatrix = Method.Gauss(A, b);

        resultMatrix.printMatrix();

        System.out.println();
    }
}
