package com.haulmont.testtask.classes;

import java.util.Date;

public class Order {
    private long orderID;
    private long clientID;
    private long mechanicID;
    private Date creationDate;
    private Date workingFinishDate;
    private double cost;
    private double status;

    public Order(long orderID, long clientID, long mechanicID, Date creationDate, Date workingFinishDate, double cost, double status) {
        this.orderID = orderID;
        this.clientID = clientID;
        this.mechanicID = mechanicID;
        this.creationDate = creationDate;
        this.workingFinishDate = workingFinishDate;
        this.cost = cost;
        this.status = status;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public long getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(long mechanicID) {
        this.mechanicID = mechanicID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getWorkingFinishDate() {
        return workingFinishDate;
    }

    public void setWorkingFinishDate(Date workingFinishDate) {
        this.workingFinishDate = workingFinishDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getStatus() {
        return status;
    }

    public void setStatus(double status) {
        this.status = status;
    }
}
