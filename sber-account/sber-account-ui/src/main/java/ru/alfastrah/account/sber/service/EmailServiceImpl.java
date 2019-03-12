package ru.alfastrah.account.sber.service;

import com.vaadin.spring.annotation.VaadinSessionScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.model.mail.AttachMailData;
import ru.alfastrah.account.sber.backend.model.mail.ChatMailData;
import ru.alfastrah.account.sber.backend.model.mail.MailAttachment;
import ru.alfastrah.account.sber.backend.model.mail.MailEntity;
import ru.alfastrah.account.sber.backend.service.MailService;
import ru.alfastrah.account.sber.model.ChatQuestion;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@VaadinSessionScope
public class EmailServiceImpl implements EmailService {
    private MailService mailService;

    @Autowired
    public EmailServiceImpl(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void sendEmailWithAttachment(List<ChatQuestion> questionList) throws MessagingException {
        for (ChatQuestion question : questionList) {
            sendEmail(question);
        }
    }

    @Override
    public void sendEmailWithAttachment(String reciver, File file, AttachMailData mailData) throws MessagingException {
        MailEntity mail = new MailEntity();
        mail.setRecipient(reciver + ";" + mailData.getRecipients());
        mail.setSubject(mailData.getSubject());
        mail.setText(mailData.getText());

        if (file != null) {
            MailAttachment mailAttachment = new MailAttachment();
            mailAttachment.setFilename("relativeList.xls");
            mailAttachment.setFile(file);
            mail.getAttachmentList().add(mailAttachment);
        }

        mailService.sendMailWithAttach(mail);
    }

    @Override
    public void sendEmail(String reciver, String subject, String text) throws MessagingException {
        MailEntity mail = new MailEntity();
        mail.setRecipient(reciver);
        mail.setSubject(subject);
        mail.setText(text);

        mailService.sendMail(mail);
    }

    private void sendEmail(ChatQuestion question) throws MessagingException {
        ChatMailData mailData = question.getMailData();
        if (mailData != null) {
            MailEntity mail = new MailEntity();
            mail.setRecipient(mailData.getRecipients());
            mail.setSubject(mailData.getMailSubject());
            mail.setText(StringUtils.join(Arrays.asList(mailData.getMailBody(), question.getQuestion()), "<br>"));

            if (StringUtils.isNotBlank(question.getAttachName()) && Objects.nonNull(question.getAttach())) {
                MailAttachment mailAttachment = new MailAttachment();
                mailAttachment.setFilename(question.getAttachName());
                mailAttachment.setFile(question.getAttach());
                mail.getAttachmentList().add(mailAttachment);
            }

            mailService.sendMailWithAttach(mail);
        }
    }
}
