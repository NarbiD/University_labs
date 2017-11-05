package DBMS.table;

import java.io.File;
import java.io.IOException;

public class Tables {

    public static boolean isTableExists(String name, String location) {
        File table = new File(location + name);
        return table.isFile();
    }

    public static void delete(String name, String location) throws IOException {
        if (isTableExists(name, location)) {
            File table  = new File(location + name);
            if(!table.delete()) {
                throw new IOException("Can not delete table from storage");
            }
        }
    }
}
