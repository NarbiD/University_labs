import service.DBException;
import service.DBService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws DBException, SQLException {
        DBService dbService = new DBService();
        dbService.addLesson("MMDI", "Kulyan", "8", "Friday");
        System.out.println(dbService.getTeachersByDayAndRoom("Friday", "8"));

    }
}
