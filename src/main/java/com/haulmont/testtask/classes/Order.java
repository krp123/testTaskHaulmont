package com.haulmont.testtask.classes;

import java.util.Date;

public class Order {
    private long orderID;
    private String clientLastName;
    private String mechanicLastName;
    private Date creationDate;
    private Date completeDate;
    private String cost;
    private String status;

    public Order(long orderID, String clientLastName, String mechanicLastName, Date creationDate, Date completeDate, String cost, String status) {
        this.orderID = orderID;
        this.clientLastName = clientLastName;
        this.mechanicLastName = mechanicLastName;
        this.creationDate = creationDate;
        this.completeDate = completeDate;
        this.cost = cost;
        this.status = status;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getMechanicLastName() {
        return mechanicLastName;
    }

    public void setMechanicLastName(String mechanicLastName) {
        this.mechanicLastName = mechanicLastName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
