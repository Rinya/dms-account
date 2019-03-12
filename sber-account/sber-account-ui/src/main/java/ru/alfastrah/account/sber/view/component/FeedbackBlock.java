package ru.alfastrah.account.sber.view.component;

import com.vaadin.data.HasValue;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.model.UIText;
import ru.alfastrah.account.sber.service.EmailService;
import ru.alfastrah.account.sber.styles.view.ContractStyle;

import javax.mail.MessagingException;

import static ru.alfastrah.account.sber.helper.NotificationHelper.showErrorNotification;

public class FeedbackBlock extends VerticalLayout implements HasLogger {
    private TextField emailField;
    private CheckBox feedbackCheckbox;
    private EmailService emailService;
    private UIText uiText;

    public FeedbackBlock(EmailService emailService, UIText uiText) {
        this.emailService = emailService;
        this.uiText = uiText;

        buildLayout();
    }

    public void sendFeedback() {
        if (feedbackCheckbox.getValue()) {
            if (emailField != null && StringUtils.isNotBlank(emailField.getValue())){
                try {
                    emailService.sendEmail(emailField.getValue(), uiText.getTitle(), uiText.getMessage());
                } catch (MessagingException e) {
                    getLogger().error("sending email exception {}", e);
                    showErrorNotification("Не удалось отправить сообщение", e);
                }
            } else {
                Notification.show("Не указан почтовый ящик для обратной связи", Notification.Type.ERROR_MESSAGE);
            }
        }
    }

    public void clear() {
        emailField.clear();
        feedbackCheckbox.setValue(false);
    }

    private void buildLayout() {
        setId("feedback-layout");
        setMargin(false);
        addStyleName(ContractStyle.POLICY_CARD);
        setSizeUndefined();

        emailField = new TextField();
        emailField.setPlaceholder("E-mail");
        emailField.setVisible(false);
        emailField.setWidth(300, Unit.PIXELS);

        feedbackCheckbox = new CheckBox("Получить обратную связь");
        feedbackCheckbox.setId("feedback-checkbox");
        feedbackCheckbox.addValueChangeListener((HasValue.ValueChangeListener<Boolean>) event ->
                emailField.setVisible(event.getValue()));

        addComponents(feedbackCheckbox, emailField);
    }
}
