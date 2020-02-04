package com.haulmont.testtask.dao;

import com.haulmont.testtask.classes.Client;

import java.util.List;

public interface ClientDao {

    public void create(String firstName, String lastName, String middleName, String phone);

    public void update(long clientID, String firstName, String lastName, String middleName, String phone);

    public void delete(long clientID);

    List<Client> getAll();
}
