package com.haulmont.testtask.dao;

import com.haulmont.testtask.classes.Mechanic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            Statement statement = createConnectionAndStatement();
            statement.execute(query);
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Statement createConnectionAndStatement() throws SQLException {
        connection = HsqldbDaoFactory.createConnection();
        return connection.createStatement();
    }

    private void closeConnectionAndStatement(Statement statement) throws SQLException {
        statement.close();
        HsqldbDaoFactory.closeConnection(connection);
    }

    @Override
    public void delete(long mechanicID) {
        try {
            Statement statement = createConnectionAndStatement();
            ResultSet rs = statement.executeQuery("SELECET * FROM mechanics WHERE mechanic_id = " + mechanicID);
            if (!rs.next())
                statement.executeUpdate("DELETE FROM mechanics WHERE mechanic_id = " + mechanicID);
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Mechanic> getAll() {
        List<Mechanic> mechanics = new ArrayList<>();
        try {
            Statement statement = createConnectionAndStatement();
            ResultSet rs = statement.executeQuery("SELECET * FROM mechanics ");
            while (rs.next()) {
                mechanics.add(new Mechanic(rs.getLong(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getDouble(5)));
            }
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return mechanics;
        }
    }

    public long getID(String lastName) {
        long res = 0;
        try {
            Statement statement = createConnectionAndStatement();
            ResultSet rs = statement.executeQuery("SELECT mechanic_id FROM mechanics WHERE last_name = '" + lastName + "'");
            if (rs.next())
                rs.getLong(1);
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }
}
