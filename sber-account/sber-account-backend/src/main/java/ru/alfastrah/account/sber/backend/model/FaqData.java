package ru.alfastrah.account.sber.backend.model;

public class FaqData {
    private String title;
    private String message;

    public FaqData() {
    }

    public FaqData(String title, String message) {
        this.title = title;
        this.message = message;
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

    @Override
    public String toString() {
        return "FaqData{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
