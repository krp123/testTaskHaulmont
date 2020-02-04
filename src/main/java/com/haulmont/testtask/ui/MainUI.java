package com.haulmont.testtask.ui;

import com.haulmont.testtask.dao.DAOFactory;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        TabSheet tabsheet = new TabSheet();
        tabsheet.setSizeUndefined();


        layout.addComponent(new Label("Main UI"));
    }

    public VerticalLayout  orders(VaadinRequest request) {
        VerticalLayout panelContent = new VerticalLayout();
        panelContent.setSizeFull();

        Table ordersTable = new Table();

        ordersTable.addContainerProperty("orderID", Long.class, null);
        ordersTable.addContainerProperty("clientID", Long.class, null);
        ordersTable.addContainerProperty("mechanicID", Long.class, null);
        ordersTable.addContainerProperty("creationDate", Date.class, null);
        ordersTable.addContainerProperty("workingFinishDate", Date.class, null);
        ordersTable.addContainerProperty("cost", Double.class, null);
        ordersTable.addContainerProperty("status", Double.class, null);
        ordersTable.addContainerProperty("create", Button.class, null);
        ordersTable.addContainerProperty("edit", Button.class, null);
        ordersTable.addContainerProperty("delete", Button.class, null);

        ordersTable.setColumnHeader("creationDate", "Дата создания");
        ordersTable.setColumnHeader("workingFinishDate", "Дата окончания работ");
        ordersTable.setColumnHeader("cost", "Цена");
        ordersTable.setColumnHeader("status", "Статус");
        ordersTable.setColumnHeader("create", "");
        ordersTable.setColumnHeader("edit", "");
        ordersTable.setColumnHeader("delete", "");

        DAOFactory daoFactory = DAOFactory.getDaoFactory();

        return panelContent;
    }
}