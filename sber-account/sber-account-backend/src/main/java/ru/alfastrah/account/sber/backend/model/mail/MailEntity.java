package ru.alfastrah.account.sber.backend.model.mail;

import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

public class MailEntity {
    private String recipient;
    private String subject;
    private String text;
    private List<MailAttachment> attachmentList;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

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

    public List<MailAttachment> getAttachmentList() {
        if (CollectionUtils.isEmpty(attachmentList)) {
            attachmentList = new LinkedList<>();
        }
        return attachmentList;
    }

    public void setAttachmentList(List<MailAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    @Override
    public String toString() {
        return "MailEntity{" +
                "recipient='" + recipient + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", attachmentList=" + attachmentList +
                '}';
    }
}
