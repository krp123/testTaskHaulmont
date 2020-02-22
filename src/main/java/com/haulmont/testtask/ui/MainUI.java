package com.haulmont.testtask.ui;

import com.haulmont.testtask.classes.Client;
import com.haulmont.testtask.classes.Mechanic;
import com.haulmont.testtask.classes.Order;
import com.haulmont.testtask.classes.Validator;
import com.haulmont.testtask.dao.ClientDao;
import com.haulmont.testtask.dao.DAOFactory;
import com.haulmont.testtask.dao.MechanicDao;
import com.haulmont.testtask.dao.OrderDao;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        MechanicDao mechanicDao = daoFactory.getMechanicDao();
        List<Mechanic> mechanicList = mechanicDao.getAll();

        ClientDao clientDao = daoFactory.getClientDao();
        List<Client> clientList = clientDao.getAll();

        //заполнение таблицы
        int itemIndex = 1;
        for (Order order : ordersList) {
            Button buttonEdit = new Button("Изменить");
            Button buttonDelete = new Button("Удалить");
            ordersTable.addItem(new Object[]{
                    order.getOrderID(),
                    order.getClientLastName(),
                    order.getMechanicLastName(),
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

                    //Добавляем поля в экран изменения

                    NativeSelect mechanicSelect = new NativeSelect("Механик");
                    List<String> mechanicListString = null;
                    for (Mechanic mechanic : mechanicList) {
                        mechanicListString.add(mechanic.getLastName());
                    }
                    mechanicSelect.addItems(mechanicListString);
                    mechanicSelect.setValue(item.getItemProperty("mechanicLastName"));
                    mechanicSelect.setNullSelectionAllowed(false);

                    NativeSelect clientSelect = new NativeSelect("Клиент");
                    List<String> clientListString = null;
                    for (Client client : clientList) {
                        clientListString.add(client.getLastName());
                    }
                    clientSelect.addItems(mechanicListString);
                    clientSelect.setValue(item.getItemProperty("clientLastName"));
                    clientSelect.setNullSelectionAllowed(false);

                    DateField creationDate = createDateField(item, "Дата создания", "creationDate");

                    DateField completeDate = createDateField(item, "Дата завершения", "completeDate");

                    TextField cost = new TextField("Стоимость");
                    cost.setValue(item.getItemProperty("cost").getValue().toString());

                    NativeSelect status = new NativeSelect("Статус");
                    status.setValue(item.getItemProperty("status").getValue().toString());

                    subContent.addComponents(mechanicSelect, clientSelect, creationDate, completeDate, cost, status);

                    //обработчик кнопки Ок
                    Button buttonOk = new Button("Ок");
                    buttonOk.addClickListener(new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent) {
                            //Валидация введенных данных
                            UserError stringError = new UserError("Поле должно содержать только буквы");
                            UserError dateError = new UserError("Неверный формат даты");
                            UserError priceError = new UserError("Неверный числовой формат");
                            boolean haveError = false;

                            if (!Validator.dateValidator(creationDate.getValue().toString())) {
                                creationDate.setComponentError(dateError);
                                haveError = true;
                            } else
                                creationDate.setComponentError(null);

                            if (!Validator.dateValidator(completeDate.getValue().toString())) {
                                completeDate.setComponentError(dateError);
                                haveError = true;
                            } else
                                completeDate.setComponentError(null);

                            if (!Validator.priceValidator(cost.getValue())) {
                                cost.setComponentError(priceError);
                                haveError = true;
                            } else
                                cost.setComponentError(null);

                            //записываем данные
                            if (!haveError) {
                                long orderId = Long.parseLong(item.getItemProperty("id").getValue().toString());
                                orderDao.update(orderId, clientSelect.getValue().toString(),
                                        mechanicSelect.getValue().toString(), creationDate.getValue(),
                                        completeDate.getValue(), cost.getValue(),
                                        status.getValue().toString());
                            }
                        }
                    });
                    subContent.addComponent(buttonOk);
                    subContent.addComponent(createCloseButton(subWindow));
                    addWindow(subWindow);
                }
            });
        }

        return panelContent;
    }

    private static DateField createDateField(Item item, String dateFieldName, String dateFieldDAOName) {
        DateField dateField = new DateField(dateFieldDAOName);
        dateField.setDateFormat("dd.mm.yyyy");
        String creationDateString = item.getItemProperty(dateFieldName).getValue().toString();
        Date dateData = null;
        try {
            dateData = parseDate(creationDateString, "dd.mm.yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateField.setValue(dateData);
        return dateField;
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

    private static Date parseDate(String date, String format) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}