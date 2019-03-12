package ru.alfastrah.account.sber.backend.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.model.mail.MailAttachment;
import ru.alfastrah.account.sber.backend.model.mail.MailEntity;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    private JavaMailSender emailSender;
    @Value("${email.from}")
    private String emailFrom;
    @Value("${email.debug:false}")
    private boolean emailDebug;
    @Value("${email.debug.recipient:}")
    private String emailDebugRecipient;

    @Autowired
    public MailServiceImpl(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendMail(MailEntity mail) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");

        helper.setFrom(emailFrom);
        helper.setTo(getRecipient(mail));
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getText(), true);

        emailSender.send(message);
    }

    @Override
    public void sendMailWithAttach(MailEntity mail) throws MessagingException {
        getLogger().trace("Enter to sendMailWithAttach emailDebug {} emailDebugRecipient {}", emailDebug, emailDebugRecipient);
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

        helper.setFrom(emailFrom);
        helper.setTo(getRecipient(mail));
        helper.setSubject(mail.getSubject());
        helper.setText(mail.getText(), true);

        for (MailAttachment attachment : mail.getAttachmentList()) {
            helper.addAttachment(attachment.getFilename(), attachment.getFile());
        }

        emailSender.send(message);
    }

    private String[] getRecipient(MailEntity mail) {
        return StringUtils.isNotBlank(emailDebugRecipient)? StringUtils.split(emailDebugRecipient, ";") :
                StringUtils.split(mail.getRecipient(), ";");
    }
}
