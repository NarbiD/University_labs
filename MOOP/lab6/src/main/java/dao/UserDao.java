package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao extends Dao {
    public UserDao(Connection connection) {
        super(connection);
    }

    public void insertUser (String name, String password) throws SQLException {
        executor.execUpdate("insert into user (name, password) values ('" + name + "', '"
                + password + "')");
    }

    public boolean isExist(String name, String pass) throws SQLException {
        return executor.execQuery("select * from user where name='" + name + "' and password='" + pass + "'",
                resultSet -> resultSet.next());
    }


}
