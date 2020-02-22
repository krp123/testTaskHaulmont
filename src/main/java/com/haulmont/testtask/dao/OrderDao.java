package com.haulmont.testtask.dao;

import com.haulmont.testtask.classes.Order;

import java.util.Date;
import java.util.List;

public interface OrderDao {
    public void create(String clientLastName, String mechanicLastName, Date creationDate, Date completeDate,
                       String cost, String status);

    public void update(long orderID, String clientLastName, String mechanicLastName, Date creationDate, Date completeDate,
                       String cost, String status);

    public void delete(long orderID);

    public List<Order> getAll();
}
