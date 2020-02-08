package com.haulmont.testtask.ui;

import com.haulmont.testtask.classes.Order;
import com.haulmont.testtask.dao.DAOFactory;
import com.haulmont.testtask.dao.OrderDao;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.Date;
import java.util.List;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        TabSheet tabsheet = new TabSheet();
        tabsheet.setSizeUndefined();
        tabsheet.addTab(orders(request), "Список заказов");


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
        ordersTable.addContainerProperty("completeDate", Date.class, null);
        ordersTable.addContainerProperty("cost", Double.class, null);
        ordersTable.addContainerProperty("status", Double.class, null);
        ordersTable.addContainerProperty("create", Button.class, null);
        ordersTable.addContainerProperty("edit", Button.class, null);
        ordersTable.addContainerProperty("delete", Button.class, null);

        ordersTable.setColumnHeader("creationDate", "Дата создания");
        ordersTable.setColumnHeader("completeDate", "Дата окончания работ");
        ordersTable.setColumnHeader("cost", "Цена");
        ordersTable.setColumnHeader("status", "Статус");
        ordersTable.setColumnHeader("create", "");
        ordersTable.setColumnHeader("edit", "");
        ordersTable.setColumnHeader("delete", "");

        DAOFactory daoFactory = DAOFactory.getDaoFactory();
        OrderDao orderDao = daoFactory.getOrderDao();
        List<Order> ordersList = orderDao.getAll();

        //заполнение таблицы
        int itemIndex = 1;
        for (Order order : ordersList) {
            Button buttonEdit = new Button("Изменить");
            Button buttonDelete = new Button("Удалить");
            ordersTable.addItem(new Object[]{
                    order.getOrderID(),
                    order.getClientID(),
                    order.getMechanicID(),
                    order.getCreationDate(),
                    order.getCompleteDate(),
                    order.getCost(),
                    order.getStatus(),
                    buttonEdit,
                    buttonDelete
            }, itemIndex);
            final int index = itemIndex;
            itemIndex++;

            //обработчик кнопки "Удалить"
            buttonDelete.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    Window subWindow = createModalWindow("Удаление заказа");
                    FormLayout subContent = (FormLayout) subWindow.getContent();
                    subContent.addComponent(new Label("Вы действительно хотите удалить заказ?"));

                    Button ok = new Button("Ок");
                    ok.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent) {
                            Item item = ordersTable.getItem(index);
                            orderDao.delete(Long.parseLong(item.getItemProperty("id").getValue().toString()));
                            subWindow.close();
                            init(request);
                        }
                    });
                    subContent.addComponent(ok);
                    subContent.addComponent(createCloseButton(subWindow));
                    addWindow(subWindow);
                }
            });

            //обработчик кнопки "Изменить"
            buttonEdit.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    Window subWindow = createModalWindow("Изменение заказа");
                    FormLayout subContent = (FormLayout) subWindow.getContent();
                    Item item = ordersTable.getItem(index);

                    //Для полей механик и клиент добавить выпадающие списки. Дата создания и заверщения - даты
                    //Цена - числовое поле. Статус - перечисление.

                }
            });


        }

        return panelContent;
    }

    public static Window createModalWindow(String title) {
        Window subWindow = new Window(title);
        FormLayout subContent = new FormLayout();
        subContent.setMargin(true);
        subWindow.setContent(subContent);
        subWindow.setModal(true);
        subWindow.setWidth("480px");
        subWindow.setResizable(false);
        subWindow.center();
        subContent.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        return subWindow;

    }

    public static Button createCloseButton(Window window) {
        Button close = new Button();
        close.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                window.close();
            }
        });
        return close;
    }
}