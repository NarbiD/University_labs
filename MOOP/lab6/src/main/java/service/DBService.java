package service;

import dao.*;
import dataset.Room;
import dataset.Teacher;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBService {
    private static Connection connection = Connector.getMysqlConnection();

    public static void addSubject(String name) throws DBException {
        try {
            SubjectDao dao = new SubjectDao(connection);
            dao.insertSubject(name);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        }
    }

    public static void addTeacher(String name) throws DBException {
        try {
            TeacherDao dao = new TeacherDao(connection);
            dao.insertTeacher(name);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        }
    }

    public static void addRoom(String number) throws DBException {
        try {
            RoomDao dao = new RoomDao(connection);
            dao.insertRoom(number);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        }
    }

    public static void addUser(String name, String password) throws DBException {
        try {
            UserDao dao = new UserDao(connection);
            dao.insertUser(name, password);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        }
    }

    public static void addLesson(String subject, String teacher, String room, String day) throws DBException {
        try {
           LessonDao dao = new LessonDao(connection);
            dao.insertLesson(subject, teacher, room, day);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ignore) {
            }
            throw new DBException(e);
        }
    }

    public static List<Teacher> getTeachersByDayAndRoom(String day, String room) throws SQLException {
        return new TeacherDao(connection).getTeacherByDayAndRoom(day, room);
    }

    public static List<Teacher> getTeacherByDayWithoutLesson(String day) throws SQLException {
        return new TeacherDao(connection).getTeacherByDayWithoutLesson(day);
    }

    public static List<Room> getRoomByDay(String day) {
        return new ArrayList<>();
    }

    public static boolean isUserExist(String name, String password) throws SQLException {
        return new UserDao(connection).isExist(name, password);
    }
}
