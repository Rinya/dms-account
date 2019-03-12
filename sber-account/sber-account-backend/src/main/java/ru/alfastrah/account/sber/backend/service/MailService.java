package ru.alfastrah.account.sber.backend.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.model.mail.MailEntity;

import javax.mail.MessagingException;
import java.io.Serializable;

public interface MailService extends Serializable, HasLogger {
    void sendMail(MailEntity mail) throws MessagingException;

    void sendMailWithAttach(MailEntity mail) throws MessagingException;
}
