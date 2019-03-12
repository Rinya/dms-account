package ru.alfastrah.account.sber.model;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Passport implements Serializable {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private String seria;
    private String number;
    private LocalDate dateIssue;
    private String placeIssue;

    public String getSeria() {
        return seria;
    }

    public void setSeria(String seria) {
        this.seria = seria;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDateIssue() {
        return dateIssue;
    }

    public void setDateIssue(LocalDate dateIssue) {
        this.dateIssue = dateIssue;
    }

    public String getPlaceIssue() {
        return placeIssue;
    }

    public void setPlaceIssue(String placeIssue) {
        this.placeIssue = placeIssue;
    }

    public String getPassport() {
        List<String> list = new LinkedList<>();
        list.add("Паспорт");

        if (StringUtils.isNotBlank(seria)) {
            list.add("серия: " + seria);
        }

        if (StringUtils.isNotBlank(number)) {
            list.add("номер: " + number);
        }

        if (Objects.nonNull(dateIssue)) {
            list.add("дата выдачи: " + dateIssue.format(formatter));
        }

        if (StringUtils.isNotBlank(placeIssue)) {
            list.add("кем выдан: " + placeIssue);
        }

        return list.stream().collect(Collectors.joining(StringUtils.SPACE));
    }

    public boolean isEmpty() {
        return StringUtils.isBlank(seria) && StringUtils.isBlank(number)
                && StringUtils.isBlank(placeIssue) && Objects.isNull(dateIssue);
    }

    @Override
    public String toString() {
        return "Passport{" +
                "seria='" + seria + '\'' +
                ", number='" + number + '\'' +
                ", dateIssue=" + dateIssue +
                ", placeIssue='" + placeIssue + '\'' +
                '}';
    }
}
