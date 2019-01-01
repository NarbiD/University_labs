package dao;

import executor.Executor;

import java.sql.Connection;

public class Dao {
    Executor executor;

    public Dao(Connection connection) {
        this.executor = new Executor(connection);
    }

}
