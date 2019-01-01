package dao;

import dataset.Room;
import executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

public class RoomDao {

    private Executor executor;

    public RoomDao(Connection connection) {
        this.executor = new Executor(connection);
    }

    public void insertRoom (String number) throws SQLException {
        executor.execUpdate("insert into subject (number) values ('" + number + "')");
    }

    public int getID(String roomNum) throws SQLException {
        return this.executor.execQuery("select * from room where number='" + roomNum+"'", resultSet -> {
            resultSet.next();
            return new Room(resultSet.getInt(1), resultSet.getString(2));
        }).getId();
    }
}
