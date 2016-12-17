package Linear_systems;

import java.io.*;
import java.lang.String;
import java.util.Locale;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        String fileName = "C:\\eSupport\\q.txt";
        Scanner scanner = new Scanner(new InputStreamReader(new FileInputStream(new File(fileName)).useLocale(Locale.US)));
        int size = scanner.nextInt();
        Matrix A = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double value = scanner.nextDouble();
                A.set(i, j, value);
            }
        }
        Matrix b = new Matrix(size, 1);
        for (int i = 0; i < size; i++) {
            double value = scanner.nextDouble();
            b.set(i, 0, value);
        }
        Matrix resultMatrix = Method.Gauss(A, b);
        resultMatrix.printMatrix();
        System.out.println();
    }
}
