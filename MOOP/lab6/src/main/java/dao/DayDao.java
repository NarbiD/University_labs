package dao;

import dataset.Day;

import java.sql.Connection;
import java.sql.SQLException;

public class DayDao extends Dao{


    public DayDao(Connection connection) {
        super(connection);
    }

    public int getID(String name) throws SQLException {
        return this.executor.execQuery("select * from day where name='" + name+"'", resultSet -> {
            resultSet.next();
            return new Day(resultSet.getInt(1), resultSet.getString(2));
        }).getId();
    }
}
