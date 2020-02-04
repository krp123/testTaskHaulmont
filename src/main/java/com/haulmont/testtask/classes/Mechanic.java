package com.haulmont.testtask.classes;

public class Mechanic {

    private long mechanicID;
    private String firstName;
    private String lastName;
    private String middleName;
    private double hourSalary;

    public Mechanic(long mechanicID, String firstName, String lastName, String middleName, double hourSalary) {
        this.mechanicID = mechanicID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.hourSalary = hourSalary;
    }

    public long getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(long mechanicID) {
        this.mechanicID = mechanicID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public double getHourSalary() {
        return hourSalary;
    }

    public void setHourSalary(double hourSalary) {
        this.hourSalary = hourSalary;
    }
}
