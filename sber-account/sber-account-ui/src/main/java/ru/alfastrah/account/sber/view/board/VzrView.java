package ru.alfastrah.account.sber.view.board;

import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.backend.model.mail.AttachMailData;
import ru.alfastrah.account.sber.model.Insured;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.VzrItem;
import ru.alfastrah.account.sber.service.EmailService;
import ru.alfastrah.account.sber.service.PolicyService;
import ru.alfastrah.account.sber.view.component.VzrForm;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

import static ru.alfastrah.account.sber.constants.BoardMenu.VZR;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showErrorNotification;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showSuccessNotification;

@SpringView(name = VzrView.VIEW_NAME, ui = AppUI.class)
@UIScope
public class VzrView extends MainView {
    public static final String VIEW_NAME = "vzr";
    private PolicyService policyService;
    private InsuredProfile profile;
    private VzrForm vzrForm;

    private EmailService emailService;

    @Autowired
    public VzrView(BoardMenuView menu, PolicyService policyService, InsuredProfile profile, EmailService emailService) {
        super(menu);
        this.policyService = policyService;
        this.profile = profile;
        this.emailService = emailService;
    }

    @Override
    protected String getViewDescription() {
        return VZR.getName();
    }

    @Override
    protected Resource getViewIcon() {
        return VZR.getIcon();
    }

    @PostConstruct
    private void init() {
        vzrForm = new VzrForm(profile, getInsuredPolicy());
        vzrForm.fillFromProfile();
        getContent().addComponent(vzrForm);
        getContent().addComponent(createSendButton());
    }

    private Optional<Insured> getInsuredPolicy() {
        List<Insured> policyList = policyService.policyList(profile.getPhone());
        return policyList.stream().filter(item -> profile.getPolicyId().equals(item.getPolicyId())
                && profile.getUserId().equals(item.getUserId())).findFirst();
    }

    private Button createSendButton() {
        Button button = new Button("ОТПРАВИТЬ");
        button.setId("send-button");
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                sendEmail();
                showSuccessNotification("Заявление на бонусный ВЗР успешно отправлено");
            } catch (MessagingException e) {
                getLogger().error("bonus vzr send email exception {}", e);
                showErrorNotification(e);
            }
        });
        return button;
    }

    private void sendEmail() throws MessagingException {
        getLogger().trace("VZR-email: {}", profile.getEmailVzrReciver());
        AttachMailData mailData = policyService.getVzrMailData(profile);

        getLogger().trace("VZR-mailData: {}", mailData);
        emailService.sendEmail(profile.getEmailVzrReciver(), mailData.getSubject(), createMailBody(mailData));
    }

    private String createMailBody(AttachMailData mailData) {
        VzrItem vzrItem = vzrForm.getItem();
        getLogger().trace("VZR-item: {}", vzrItem);

        StringBuilder mailBody = new StringBuilder(mailData.getText());
        mailBody
                .append("<br><br>")
                .append("Для Бонусного полиса ВЗР. Латинское написание Имени и Отчества (как в загранпаспорте): ")
                .append(vzrItem.getLatinFullName())
                .append("<br>")
                .append("Для Бонусного полиса ВЗР. Дата рождения: ")
                .append(vzrItem.getBirthDate())
                .append("<br>")
                .append("Для Бонусного полиса ВЗР. Срок окончания Бонусного полиса ВЗР: ")
                .append(vzrItem.getPolicyDuration())
                .append("<br>")
                .append("Для Бонусного полиса ВЗР. Страна: ")
                .append(vzrItem.getCountry())
                .append("<br>");
        return mailBody.toString();
    }
}
