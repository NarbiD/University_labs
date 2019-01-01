package dao;

import dataset.Subject;
import executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class SubjectDao {

    private Executor executor;

    public SubjectDao(Connection connection) {
        this.executor = new Executor(connection);
    }

    public void insertSubject (String name) throws SQLException {
        executor.execUpdate("insert into subject (name) values ('" + name + "')");
    }

    public int getID(String subject) throws SQLException {
        return this.executor.execQuery("select * from subject where name='" + subject+"'", resultSet -> {
            resultSet.next();
            return new Subject(resultSet.getInt(1), resultSet.getString(2));
        }).getId();
    }
}
