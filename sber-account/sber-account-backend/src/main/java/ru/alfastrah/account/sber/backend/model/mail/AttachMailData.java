package ru.alfastrah.account.sber.backend.model.mail;

public class AttachMailData {
    private String subject;
    private String text;
    private String recipients;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    @Override
    public String toString() {
        return "AttachMailData{" +
                "subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", recipients='" + recipients + '\'' +
                '}';
    }
}
