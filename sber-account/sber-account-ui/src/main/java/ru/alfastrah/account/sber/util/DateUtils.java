package ru.alfastrah.account.sber.util;

import ru.alfastrah.account.sber.backend.HasLogger;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class DateUtils implements Serializable, HasLogger {
    private static final List<DateTimeFormatter> dateFormats;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private DateUtils() {
    }

    static {
        dateFormats = new LinkedList<>();

        dateFormats.add(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d.MM.yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dd.M.yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d.M.yyyy"));

        dateFormats.add(DateTimeFormatter.ofPattern("dd.MM.yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d.MM.yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dd.M.yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d.M.yy"));

        dateFormats.add(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d-MM-yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dd-M-yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d-M-yyyy"));

        dateFormats.add(DateTimeFormatter.ofPattern("dd-MM-yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d-MM-yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dd-M-yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d-M-yy"));

        dateFormats.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d/MM/yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dd/M/yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d/M/yyyy"));

        dateFormats.add(DateTimeFormatter.ofPattern("dd/MM/yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d/MM/yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dd/M/yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d/M/yy"));

        dateFormats.add(DateTimeFormatter.ofPattern("dd MM yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d MM yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dd M yyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d M yyyy"));

        dateFormats.add(DateTimeFormatter.ofPattern("dd MM yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d MM yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dd M yy"));
        dateFormats.add(DateTimeFormatter.ofPattern("d M yy"));

        dateFormats.add(DateTimeFormatter.ofPattern("ddMMyyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dMMyyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("ddMyyyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dMyyyy"));

        dateFormats.add(DateTimeFormatter.ofPattern("ddMMyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dMMyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("ddMyy"));
        dateFormats.add(DateTimeFormatter.ofPattern("dMyy"));
    }

    public static LocalDate parseDate(String stringDate) {
        for (DateTimeFormatter value : dateFormats) {
            try {
                return LocalDate.parse(stringDate, value);
            } catch (Exception e) {
                //log.warn("DateUtils.parseDate exception {}", e);
            }
        }

        return null;
    }

    public static String localDateToString(LocalDate date) {
        return date != null? date.format(formatter) : "";
    }
}
