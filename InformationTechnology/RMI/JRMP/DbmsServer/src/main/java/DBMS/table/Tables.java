package DBMS.table;

import DBMS.exception.TableException;

import java.io.File;

public class Tables {

    public static boolean isTableExists(String name, String location) {
        File table = new File(location + name);
        return table.isFile();
    }

    public static void delete(String name, String location) throws TableException {
        if (isTableExists(name, location)) {
            File table  = new File(location + name);
            if(!table.delete()) {
                throw new TableException("Can not delete table from storage");
            }
        }
    }
}
