package com.haulmont.testtask.dao;

import com.haulmont.testtask.classes.Mechanic;

import java.util.List;

public interface MechanicDao {
    public void create(String firstName, String lastName, String middleName, double hourSalary);

    public void update(long mechanicID, String firstName, String lastName, String middleName, double hourSalary);

    public void delete(long mechanicID);

    public List<Mechanic> getAll();
}
