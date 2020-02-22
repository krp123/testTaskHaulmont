package com.haulmont.testtask.classes;

public class Validator {

    public static boolean stringValidator(String str) {
        boolean ok = false;
        str = str.toLowerCase();
        char[] chars = str.toCharArray();

        for (char chr : chars) {
            if (((chr >= 'a') && (chr <= 'z')) || ((chr >= 'а') && (chr <= 'я')))
                ok = true;
            else return false;
        }
        return ok;
    }

    public static boolean phoneValidator(String phone) {
        boolean ok = false;
        phone = phone.toLowerCase();
        char[] chars = phone.toCharArray();

        for (char chr : chars) {
            if (((chr >= '0') && (chr <= '9')) || (chr == '+'))
                ok = true;
            else return false;
        }
        return ok;
    }

    public static boolean priceValidator(String price) {
        boolean ok = false;
        price = price.toLowerCase();
        char[] chars = price.toCharArray();

        for (char chr : chars) {
            if (((chr >= '0') && (chr <= '9')) || (chr == ','))
                ok = true;
            else return false;
        }
        return ok;
    }

    public static boolean dateValidator(String date) {
        boolean ok = false;
        String[] str = date.split("-");
        if (str.length == 3) {
            try {
                int year = Integer.parseInt(str[0]);
                int month = Integer.parseInt(str[1]);
                int day = Integer.parseInt(str[2]);
                if ((year >= 1920) && (year <= 2020)) {
                    if ((month >= 1) && (month <= 12)) {
                        int numDays = 0;
                        switch (month) {
                            case 1:
                            case 3:
                            case 5:
                            case 7:
                            case 8:
                            case 10:
                            case 12:
                                numDays = 31;
                                break;
                            case 4:
                            case 6:
                            case 9:
                            case 11:
                                numDays = 30;
                                break;
                            case 2:
                                if (year % 4 == 0)
                                    numDays = 29;
                                else
                                    numDays = 28;
                                break;
                        }
                        if ((day >= 1) && (day <= numDays)) {
                            ok = true;
                        }
                    }
                }
            } catch (Exception e) {
                return false;
            }
        }
        return ok;
    }
}
