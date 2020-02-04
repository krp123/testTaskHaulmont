package com.haulmont.testtask.dao;

public abstract class DAOFactory {
    public abstract ClientDao getClientDao();

    public abstract MechanicDao getMechanicDao();

    public abstract OrderDao getOrderDao();

    public static DAOFactory getDaoFactory() {
        return new HsqldbDaoFactory();
    }
}
