package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class LessonDao extends Dao {

    private Connection connection;

    public LessonDao(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public void insertLesson (String subject, String teacher, String roomNum, String day) throws SQLException {
        int sub = new SubjectDao(connection).getID(subject);
        int teach = new TeacherDao(connection).getID(teacher);
        int room =  new RoomDao(connection).getID(roomNum);
        int dayID = new DayDao(connection).getID(day);

        executor.execUpdate("insert into lesson (subject_id, teacher_id, room_id, day_id)" +
                " values ('" + sub + "', '"
                + teach + "', '"
                + room + "', '"
                + dayID + "')" );
    }


}
