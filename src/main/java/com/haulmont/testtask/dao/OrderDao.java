package com.haulmont.testtask.dao;

import com.haulmont.testtask.classes.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    public void create(long clientID, long mechanicID, Date creationDate, Date completeDate,
                       double cost, double status);

    public void update(long orderID, long clientID, long mechanicID, Date creationDate, Date completeDate,
                       double cost, double status);

    public void delete(long orderID);

    public List<Order> getAll();
}
