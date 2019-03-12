package ru.alfastrah.account.sber.model;

import ru.alfastrah.account.sber.backend.model.mail.ChatMailData;

import java.io.File;
import java.io.Serializable;

public class ChatQuestion implements Serializable {
    private ChatMailData mailData;
    private String question;
    private String attachName;
    private File attach;

    public ChatMailData getMailData() {
        return mailData;
    }

    public void setMailData(ChatMailData mailData) {
        this.mailData = mailData;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAttachName() {
        return attachName;
    }

    public void setAttachName(String attachName) {
        this.attachName = attachName;
    }

    public File getAttach() {
        return attach;
    }

    public void setAttach(File attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return "ChatQuestion{" +
                "mailData=" + mailData +
                ", question='" + question + '\'' +
                ", attachName='" + attachName + '\'' +
                ", attach=" + attach +
                '}';
    }
}
