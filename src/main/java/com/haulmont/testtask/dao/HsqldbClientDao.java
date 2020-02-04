package com.haulmont.testtask.dao;

import com.haulmont.testtask.classes.Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HsqldbClientDao implements ClientDao {

    private Connection connection;

    @Override
    public void create(String firstName, String lastName, String middleName, String phone) {
        executeQuery("INSERT INTO clients (first_name, last_name, middle_name, phone) VALUES " +
                "('" + firstName + "', '" + lastName + "', '" + middleName +"', '" + phone +"')");
    }

    @Override
    public void update(long clientID, String firstName, String lastName, String middleName, String phone) {
        executeQuery("UPDATE clients SET (first_name, last_name, middle_name, phone) = " +
                "('" + firstName + "', '" + lastName + "', '" + middleName +"', '" + phone + "')"
        + "WHERE client_id = " + clientID);
    }

    private void executeQuery(String query) {
        try {
            Statement statement = createConnectionAndStatement();
            statement.execute(query);
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long clientID) {
        try {
            Statement statement = createConnectionAndStatement();
            ResultSet rs = statement.executeQuery("SELECET * FROM clients WHERE client_id = " + clientID);
            if (!rs.next())
                statement.executeUpdate("DELETE FROM clients WHERE client_id = " + clientID);
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Client> getAll() {
        List<Client> clients = new ArrayList<>();
        try {
            Statement statement = createConnectionAndStatement();
            ResultSet rs = statement.executeQuery("SELECET * FROM clients ");
            while (rs.next()) {
                clients.add(new Client(rs.getLong(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return clients;
        }
    }

    public long getID(String phone) {
        long res = 0;
        try {
            Statement statement = createConnectionAndStatement();
            ResultSet rs = statement.executeQuery("SELECT client_id FROM clients WHERE phone = '" + phone + "'");
            if (rs.next())
                rs.getLong(1);
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return res;
        }
    }

    private void closeConnectionAndStatement(Statement statement) throws SQLException {
        statement.close();
        HsqldbDaoFactory.closeConnection(connection);
    }

    private Statement createConnectionAndStatement() throws SQLException {
        connection = HsqldbDaoFactory.createConnection();
        return connection.createStatement();
    }
}
