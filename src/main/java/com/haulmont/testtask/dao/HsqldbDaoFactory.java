package com.haulmont.testtask.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HsqldbDaoFactory extends DAOFactory{
    public HsqldbDaoFactory() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:hsqldb:file:database/stud", "SA", "");
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClientDao getClientDao() {
        return new HsqldbClientDao();
    }

    @Override
    public MechanicDao getMechanicDao() {
        return new HsqldbMechanicDao();
    }

    @Override
    public OrderDao getOrderDao() {
        return new HsqldbOrderDao();
    }
}
