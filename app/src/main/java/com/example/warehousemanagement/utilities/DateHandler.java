package com.example.warehousemanagement.utilities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHandler {
    public static LocalDate convertStringToLocalDate(String dateString) {
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date;
    }

    public static String convertLocalDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
