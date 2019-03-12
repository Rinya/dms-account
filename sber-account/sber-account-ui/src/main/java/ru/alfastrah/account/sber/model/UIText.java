package ru.alfastrah.account.sber.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class UIText implements Serializable {
    private String title;
    private String message;
    private BigDecimal formId;

    public UIText() {
    }

    public UIText(String title, String message, BigDecimal formId) {
        this.title = title;
        this.message = message;
        this.formId = formId;
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
        return "UIText{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", formId=" + formId +
                '}';
    }
}
