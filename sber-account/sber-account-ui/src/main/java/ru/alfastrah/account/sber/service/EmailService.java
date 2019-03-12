package ru.alfastrah.account.sber.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.model.mail.AttachMailData;
import ru.alfastrah.account.sber.model.ChatQuestion;

import javax.mail.MessagingException;
import java.io.File;
import java.io.Serializable;
import java.util.List;

public interface EmailService extends Serializable, HasLogger {
    void sendEmailWithAttachment(List<ChatQuestion> questionList) throws MessagingException;

    void sendEmailWithAttachment(String reciver, File file, AttachMailData mailData) throws MessagingException;

    void sendEmail(String reciver, String subject, String text) throws MessagingException;
}
