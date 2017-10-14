import java.io.*;
import java.util.Locale;
import java.util.Scanner;
import java.lang.String;

/**
 * Created by ignas on 20.12.2016.
 */

class FileReader {
    private Scanner scanner = null;

    FileReader(String fileName){
        try {
            scanner = new Scanner(new InputStreamReader(new FileInputStream(new File(fileName)))).useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readValues(Matrix inputMatrix){
        for (int i = 0; i < inputMatrix.getNumberOfRows(); i++) {
            for (int j = 0; j < inputMatrix.getNumberOfCols(); j++) {
                double value = scanner.nextDouble();
                inputMatrix.setElem(i, j, value);
            }
        }
    }
    
    void readMatrix(Matrix A, Matrix b) {
        int size = scanner.nextInt();
        A.setSize(size,size);
        A.initMatrix();
        this.readValues(A);
        b.setSize(size,1);
        b.initMatrix();
        this.readValues(b);
    }
    

}
