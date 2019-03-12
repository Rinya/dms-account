package ru.alfastrah.account.sber.view.board;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.exception.PaymentException;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.helper.ExcelHelper;
import ru.alfastrah.account.sber.helper.NotificationHelper;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.Insured;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.InsuredRelative;
import ru.alfastrah.account.sber.model.PayingItem;
import ru.alfastrah.account.sber.service.EmailService;
import ru.alfastrah.account.sber.service.PolicyService;
import ru.alfastrah.account.sber.service.PrintService;
import ru.alfastrah.account.sber.service.ProfileService;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.styles.view.ContractStyle;
import ru.alfastrah.account.sber.view.component.FeedbackBlock;
import ru.alfastrah.account.sber.view.component.InsuredRelativeLayout;
import ru.alfastrah.account.sber.view.component.PolicyInfoLayout;
import ru.alfastrah.account.sber.view.component.paying.PayingDental;
import ru.alfastrah.account.sber.view.component.paying.PayingGrid;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static ru.alfastrah.account.sber.constants.BoardMenu.DENTISTRY;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showErrorNotification;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showSuccessNotification;

@SpringView(name = DentistryView.VIEW_NAME, ui = AppUI.class)
@UIScope
public class DentistryView extends MainView {
    public static final String VIEW_NAME = "dentistry";
    public static final int FEEDBACK_TEXT_ID = 20;
    public static final int ADD_RELATIVE_TEXT = 25;
    private VerticalLayout relativeContainer;
    private InsuredProfile profile;

    private EmailService emailService;
    private UITexts uiTexts;
    private PolicyService policyService;
    private PrintService printService;
    private ProfileService profileService;

    private FeedbackBlock feedbackBlock;

    @Autowired
    public DentistryView(BoardMenuView menu, InsuredProfile profile, EmailService emailService,
                         UITexts uiTexts, PolicyService policyService, @Lazy PrintService printService,
                         ProfileService profileService) {
        super(menu);
        this.profile = profile;
        this.emailService = emailService;
        this.uiTexts = uiTexts;
        this.policyService = policyService;
        this.printService = printService;
        this.profileService = profileService;
        relativeContainer = createrelativeContainer();
    }

    @Override
    protected String getViewDescription() {
        return DENTISTRY.getName();
    }

    @Override
    protected Resource getViewIcon() {
        return DENTISTRY.getIcon();
    }

    @PostConstruct
    private void init() {
        try {
            List<Insured> dentalPolicyList = policyService.dentalPolicyList(profile.getPhone());
            dentalPolicyList.forEach(policy -> {
                PolicyInfoLayout policyInfoLayout = new PolicyInfoLayout(policy, printService, uiTexts);
                policyInfoLayout.addStyleName(CommonStyles.MAX_WIDTH_1024);
                getContent().addComponent(policyInfoLayout);
            });
        } catch (Exception e) {
            getLogger().error("policyService.dentalPolicyLis exception {}", e);
            NotificationHelper.showErrorNotification("Не удалось получить список договоров", e);
        }

        getContent().addComponent(relativeContainer);
        getContent().addComponent(createAddRelative());
        getContent().addComponent(createSendButton());

        feedbackBlock = new FeedbackBlock(emailService, uiTexts.getUiText(FEEDBACK_TEXT_ID));
        getContent().addComponent(feedbackBlock);

        PayingDental payingDentalServices = new PayingDental(printService, uiTexts,
                policyService.dentalServicesList(profile));
        payingDentalServices.addStyleName(CommonStyles.MAX_WIDTH_1024);
        getContent().addComponent(payingDentalServices);

        payingDentalServices.setListener((PayingGrid.PayingListener) (list, totalPremium) ->
                payingDental(payingDentalServices, list, totalPremium));
    }

    private VerticalLayout createrelativeContainer() {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("dental-container");
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
                showSuccessNotification("Заявление на подключение стоматологии успешно отправлено");
            } catch (Exception e) {
                getLogger().error("dentistry send email exception {}", e);
                showErrorNotification(e);
            }
        });
        return button;
    }

    private void sendEmail() throws ValidateException, IOException, MessagingException {
        List<InsuredRelative> relativeList = new LinkedList<>();

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

        feedbackBlock.sendFeedback();

        clearPage();
    }

    private void clearPage() {
        relativeContainer.removeAllComponents();
        feedbackBlock.clear();
    }

    private Button createAddRelative() {
        Button button = new Button("Добавить родственника");
        button.setId("add-relative");
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addClickListener((Button.ClickListener) clickEvent ->
                relativeContainer.addComponent(new RelativeLayout()));
        return button;
    }

    private void payingDental(PayingDental payingDental, List<PayingItem> list, BigDecimal totalPremium) {
        getLogger().trace("Enter to payingDental totalPremium {}", totalPremium);
        try {
            String redirectUrl = policyService.paymentDentalPolicies(profile, list, totalPremium);

            profileService.loadProfileData(profile.getPhone());
            uiTexts.refresh();
            payingDental.refreshList(policyService.dentalServicesList(profile));

            getUI().getPage().open(redirectUrl, "_target");
            showSuccessNotification("Оплата полиса по стоматологии успешно выполнено");
        } catch (PaymentException e) {
            getLogger().error("policyService.paymentDentalPolicies: exception {}", e);
            showErrorNotification("Не удалось оплатить полиса по стоматологии ", e);
        }
    }

    class RelativeLayout extends HorizontalLayout {
        private InsuredRelativeLayout layout;

        RelativeLayout() {
            setId("relative-block-layout");
            setSizeFull();

            VerticalLayout verticalLayout = createRelativeLayoutWithText();

            addComponent(createDeleteButton());
            addComponentsAndExpand(verticalLayout);
        }

        private VerticalLayout createRelativeLayoutWithText() {
            layout = new InsuredRelativeLayout();

            VerticalLayout verticalLayout = new VerticalLayout();
            verticalLayout.setId("relative-layout-with-text");
            verticalLayout.setMargin(false);
            verticalLayout.setCaption(layout.getCaption());
            verticalLayout.addStyleName(ContractStyle.POLICY_CARD);
            verticalLayout.addStyleName(CommonStyles.BLOCK_CAPTION);
            verticalLayout.addComponents(uiTexts.getTextComponent(ADD_RELATIVE_TEXT), layout);

            layout.setCaption("");
            layout.removeStyleName(ContractStyle.POLICY_CARD);
            return verticalLayout;
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
