package Linear_Systems;

import java.lang.String;


public class Main {

    public static void main(String[] args) {
        String fileName = "src\\Linear_Systems\\resources\\JacobiInput1.txt";
        FileReader reader = new FileReader(fileName);
        Matrix A = new Matrix();
        Matrix b = new Matrix();
        reader.readMatrix(A, b);

        System.out.println("Метод Гауса: " + Method.Gauss(A, b));
        System.out.println("Метод Якобі: " + Method.Jacobi(A, b));
        System.out.println("Метод Зейделя: " + Method.Seidel(A, b));
    }
}
