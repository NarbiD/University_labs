import java.io.File;
import java.lang.String;

public class Main {

    public static void main(String[] args) {
        String fileName = "src" + File.separator + "resources" + File.separator + "exampleInput.txt";
        FileReader reader = new FileReader(fileName);
        Matrix A = new Matrix();
        Matrix b = new Matrix();
        reader.readMatrix(A, b);

        System.out.println("Метод Гауса: " + Solver.Gauss(A, b));
        System.out.println("Метод Якобі: " + Solver.Jacobi(A, b));
        System.out.println("Метод Зейделя: " + Solver.Seidel(A, b));
    }
}
