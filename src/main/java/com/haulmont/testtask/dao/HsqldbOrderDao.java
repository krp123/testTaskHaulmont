package com.haulmont.testtask.dao;

import com.haulmont.testtask.classes.Order;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HsqldbOrderDao implements OrderDao {
    Connection connection;

    @Override
    public void create(long clientID, long mechanicID, Date creationDate, Date completeDate, double cost, double status) {
        executeQuery("INSERT INTO orders (client_id, mechanic_id, creation_date, complete_date, cost, status) VALUES" +
                "('" + clientID + "', '" + mechanicID + "', '" + creationDate + "', '" + completeDate + "'" +
                ", '" + cost + "', '" + status + "')");
    }

    @Override
    public void update(long orderID, long clientID, long mechanicID, Date creationDate, Date completeDate, double cost, double status) {
        executeQuery("UPDATE orders SET (client_id, mechanic_id, creation_date, complete_date, cost, status) = " +
                "('" + clientID + "', '" + mechanicID + "', '" + creationDate + "', '" + completeDate + "'" +
                ", '" + cost + "', '" + status + "') WHERE order_id = '" + orderID + "'");
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

    @Override
    public void delete(long orderID) {
        try {
            Statement statement = createConnectionAndStatement();
            ResultSet rs = statement.executeQuery("SELECET * FROM orders WHERE order_id = " + orderID);
            if (!rs.next())
                statement.executeUpdate("DELETE FROM orders WHERE order_id = " + orderID);
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        try {
            Statement statement = createConnectionAndStatement();
            ResultSet rs = statement.executeQuery("SELECET * FROM orders ");
            while (rs.next()) {
                orders.add(new Order(rs.getLong(1), rs.getLong(2), rs.getLong(3),
                        rs.getDate(4), rs.getDate(5), rs.getDouble(6), rs.getDouble(7)));
            }
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return orders;
        }
    }

    public long getId(String clientID) {
        long res = 0;
        try {
            Statement statement = createConnectionAndStatement();
            ResultSet rs = statement.executeQuery("SELECT order_id FROM orders WHERE client_id = '" + clientID + "'");
            if (rs.next())
                rs.getLong(1);
            closeConnectionAndStatement(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return res;
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
}
