package ru.alfastrah.account.sber.view.board;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.helper.ExcelHelper;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.InsuredRelative;
import ru.alfastrah.account.sber.service.EmailService;
import ru.alfastrah.account.sber.service.PolicyService;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.styles.view.ContractStyle;
import ru.alfastrah.account.sber.view.component.InsuredFormLayout;
import ru.alfastrah.account.sber.view.component.InsuredRelativeLayout;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static ru.alfastrah.account.sber.constants.BoardMenu.DISTANT_RELATIVE;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showErrorNotification;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showSuccessNotification;

@SpringView(name = DistantRelativeView.VIEW_NAME, ui = AppUI.class)
@UIScope
public class DistantRelativeView extends MainView {
    public static final String VIEW_NAME = "distant-relative";
    private InsuredFormLayout insuredFormLayout;
    private VerticalLayout relativeContainer;
    private InsuredProfile profile;
    private EmailService emailService;
    private UITexts uiTexts;
    private PolicyService policyService;

    @Autowired
    public DistantRelativeView(BoardMenuView menu, InsuredFormLayout insuredFormLayout,
                               InsuredProfile profile, EmailService emailService, UITexts uiTexts,
                               PolicyService policyService) {
        super(menu);
        this.insuredFormLayout = insuredFormLayout;
        this.profile = profile;
        this.emailService = emailService;
        this.uiTexts = uiTexts;
        this.policyService = policyService;
        relativeContainer = createrelativeContainer();
    }

    @Override
    protected String getViewDescription() {
        return DISTANT_RELATIVE.getName();
    }

    @Override
    protected Resource getViewIcon() {
        return DISTANT_RELATIVE.getIcon();
    }

    @PostConstruct
    private void init() {
        getContent().addComponent(insuredFormLayout);
        relativeContainer.addComponent(new InsuredRelativeLayout());
        getContent().addComponent(relativeContainer);
        getContent().addComponent(createAddRelative());
        getContent().addComponent(createSendButton());
    }

    private VerticalLayout createrelativeContainer() {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("relative-container");
        layout.setMargin(false);
        layout.setSizeUndefined();

        return layout;
    }

    private Button createSendButton() {
        Button button = new Button("ОТПРАВИТЬ");
        button.setId("send-button");
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                sendEmail();
                showSuccessNotification("Заявление на прикрепление дальных родственников успешно отправлено");
            } catch (ValidateException|IOException|MessagingException e) {
                getLogger().error("distant relative send email exception {}", e);
                showErrorNotification(e);
            }
        });
        return button;
    }

    private void sendEmail() throws ValidateException, IOException, MessagingException {
        List<InsuredRelative> relativeList = new LinkedList<>();

        insuredFormLayout.validate();
        insuredFormLayout.collapse();

        for (int i = 0; i < relativeContainer.getComponentCount(); i++) {
            Component component = relativeContainer.getComponent(i);
            if (component instanceof InsuredRelativeLayout) {
                ((InsuredRelativeLayout) component).validate();
                relativeList.add(((InsuredRelativeLayout) component).getItem());
                ((InsuredRelativeLayout) component).collapse();
            } else if (component instanceof RelativeLayout) {
                ((RelativeLayout) component).validate();
                relativeList.add(((RelativeLayout) component).getItem());
                ((RelativeLayout) component).collapse();
            }
        }

        relativeList.forEach(relative -> getLogger().trace("InsuredRelative {}", relative));
        File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), "tmp");
        ExcelHelper.createExcelForInsuredRelative(tempFile, relativeList, profile);
        emailService.sendEmailWithAttachment(profile.getEmailReciver(), tempFile,
                policyService.getAttachRelativeMailData(profile));

        emailService.sendEmail(insuredFormLayout.getEmail(), uiTexts.getTitle(20), uiTexts.getText(20));
    }

    private Button createAddRelative() {
        Button button = new Button("Добавить родственника");
        button.setId("add-relative");
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addClickListener((Button.ClickListener) clickEvent ->
                relativeContainer.addComponent(new RelativeLayout()));
        return button;
    }

    class RelativeLayout extends HorizontalLayout {
        private InsuredRelativeLayout layout;

        RelativeLayout() {
            setId("relative-block-layout");
            addStyleName(CommonStyles.MAX_WIDTH_1024);

            layout = new InsuredRelativeLayout();

            addComponent(createDeleteButton());
            addComponentsAndExpand(layout);
        }

        void validate() throws ValidateException {
            layout.validate();
        }

        public InsuredRelative getItem() {
            return layout.getItem();
        }

        public void collapse() {
            layout.collapse();
        }

        private Image createDeleteButton() {
            Image delete = new Image();
            delete.setId("delete-button");
            delete.addStyleName(ContractStyle.CLOSE_BUTTON);
            delete.addClickListener((MouseEvents.ClickListener) clickEvent ->
                    relativeContainer.removeComponent(this));

            return delete;
        }
    }
}
