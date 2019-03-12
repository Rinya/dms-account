package ru.alfastrah.account.sber.backend.model.mail;

import ru.alfastrah.account.sber.backend.constant.ChatTable;

public class ChatMailData {
    private String topic;
    private String mailSubject;
    private String mailBody;
    private String recipients;
    /***
     * Таблица, которую нужно показать клиенту, если пусто, показываем одно поле ввода
     */
    private ChatTable chatTable;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    public String getRecipients() {
        return recipients;
    }

    public void setRecipients(String recipients) {
        this.recipients = recipients;
    }

    public ChatTable getChatTable() {
        return chatTable;
    }

    public void setChatTable(Integer chatTableCode) {
        this.chatTable = ChatTable.getChatTable(chatTableCode);
    }

    @Override
    public String toString() {
        return "ChatMailData{" +
                "topic='" + topic + '\'' +
                ", mailSubject='" + mailSubject + '\'' +
                ", mailBody='" + mailBody + '\'' +
                ", recipients='" + recipients + '\'' +
                ", chatTable=" + chatTable +
                '}';
    }
}
