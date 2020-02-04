package com.haulmont.testtask.dao;

import com.haulmont.testtask.classes.Mechanic;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class HsqldbMechanicDao implements MechanicDao {

    Connection connection;

    @Override
    public void create(String firstName, String lastName, String middleName, double hourSalary) {
        executeQuery("INSERT INTO mechanics (first_name, last_name, middle_name, hour_salary) VALUES " +
                "('" + firstName + ", '" + lastName + "', '" + middleName + "', '" + hourSalary + "')");
    }

    @Override
    public void update(long mechanicID, String firstName, String lastName, String middleName, double hourSalary) {
        executeQuery("UPDATE mechanics SET (first_name, last_name, middle_name, hour_salary) = " +
                "('" + firstName + ", '" + lastName + "', '" + middleName + "', '" + hourSalary + "')" +
                "WHERE mechanic_id = " + mechanicID);
    }

    public void executeQuery(String query) {
        try {
            Statement statement = createStatementAndConnection();
            statement.execute(query);
            closeStatementAndConnection(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Statement createStatementAndConnection() throws SQLException {
        connection = HsqldbDaoFactory.createConnection();
        return connection.createStatement();
    }

    private void closeStatementAndConnection(Statement statement) throws SQLException {
        statement.close();
        HsqldbDaoFactory.closeConnection(connection);
    }

    @Override
    public void delete(long mechanicID) {

    }

    @Override
    public List<Mechanic> getAll() {
        return null;
    }
}
