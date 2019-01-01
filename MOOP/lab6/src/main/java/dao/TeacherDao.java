package dao;

import dataset.Teacher;
import executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao {
    private Executor executor;
    private Connection connection;


    public TeacherDao(Connection connection) {
        this.connection = connection;
        this.executor = new Executor(connection);
    }

    public void insertTeacher (String name) throws SQLException {
        executor.execUpdate("insert into teacher (name) values ('" + name + "')");
    }

    public int getID(String name) throws SQLException {
        return this.executor.execQuery("select * from teacher where name='" + name+"'", resultSet -> {
            resultSet.next();
            return new Teacher(resultSet.getInt(1), resultSet.getString(2));
        }).getId();
    }

    public List<Teacher> getTeacherByDayAndRoom(String day, String room) throws SQLException {
        int day_id = new DayDao(connection).getID(day);
        int room_id = new RoomDao(connection).getID(room);

        return this.executor.execQuery("select t.id, t.name from teacher as t " +
                                "join lesson as l " +
                                "on t.id=l.teacher_id " +
                                "where l.day_id=" + day_id + " and " +
                                "l.room_id=" + room_id, resultSet -> {

            List<Teacher> teachers = new ArrayList<>();
            while(resultSet.next()){
                teachers.add(new Teacher(resultSet.getInt(1), resultSet.getString(2)));
            }
            return teachers;
        });

    }

    public List<Teacher> getTeacherByDayWithoutLesson(String day) throws SQLException {
        int day_id = new DayDao(connection).getID(day);
        return this.executor.execQuery("select t.id, t.name from teacher as t " +
                "join lesson as l " +
                "on t.id=l.teacher_id " +
                "where l.day_id!=" + day_id, resultSet -> {

            List<Teacher> teachers = new ArrayList<>();
            while(resultSet.next()){
                teachers.add(new Teacher(resultSet.getInt(1), resultSet.getString(2)));
            }
            return teachers;
        });
    }
}
