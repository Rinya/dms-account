package ru.alfastrah.account.sber.backend.model;

import java.math.BigDecimal;

public class DescriptionData {
    private int id;
    private String title;
    private String message;
    private BigDecimal formId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BigDecimal getFormId() {
        return formId;
    }

    public void setFormId(BigDecimal formId) {
        this.formId = formId;
    }

    @Override
    public String toString() {
        return "DescriptionData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", formId=" + formId +
                '}';
    }
}
