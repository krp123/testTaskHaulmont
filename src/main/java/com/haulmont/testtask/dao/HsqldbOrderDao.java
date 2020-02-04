package com.haulmont.testtask.dao;

import com.haulmont.testtask.classes.Order;

import java.util.Date;
import java.util.List;

public class HsqldbOrderDao implements OrderDao {
    @Override
    public void create(long orderID, long clientID, long mechanicID, Date creationDate, Date workingFinishDate, double cost, double status) {

    }

    @Override
    public void update(long orderID, long clientID, long mechanicID, Date creationDate, Date workingFinishDate, double cost, double status) {

    }

    @Override
    public void delete(long orderID) {

    }

    @Override
    public List<Order> getAll() {
        return null;
    }
}
