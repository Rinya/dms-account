package ru.alfastrah.account.sber.pay.view;

import com.vaadin.navigator.View;
import com.vaadin.server.*;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.model.PaymentInfo;
import ru.alfastrah.account.sber.pay.HRHtml;
import ru.alfastrah.account.sber.service.PolicyService;
import ru.alfastrah.account.sber.styles.PayStyles;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@SpringComponent
@VaadinSessionScope
public class SuccessView extends VerticalLayout implements View, HasLogger {
    private static final String LAYOUT_ID = "success-payment-layout";
    private static final String IMG_LOGO_PNG = "img/logo.png";
    private static final String THANKS_VALUE = "Спасибо, что выбрали АльфаСтрахование!";
    private static final String THANKS_MOBILE_VALUE = "Спасибо, что выбрали<br>АльфаСтрахование!";
    private static final String SUCCESS_VALUE = "Ваш счет успешно оплачен";
    private static final String NUMBER_CAPTION = "Номер вашего счета";
    private static final String NUMBER_MOBILE_CAPTION = "Счет";
    private static final String COST_CAPTION = "Сумма счета";
    private static final String COST_MOBILE_CAPTION = "Стоимость";

    private Image logo;
    private Label thanksLabel;
    private Label numberLabel;
    private Label summLabel;
    private Label confirmedLabel;
    private Link returnToApplication;

    private PolicyService policyService;

    @Autowired
    public void setPolicyService(PolicyService policyService) {
        this.policyService = policyService;

        buildLayout();
    }

    public void setData(long invoiceId, String invoiceNumber) {
        numberLabel.setValue(String.valueOf(invoiceId));

        getLogger().trace("policyService {}", policyService);
        PaymentInfo paymentInfo = policyService.getPaymentInfo(invoiceId);

        summLabel.setValue(MessageFormat.format("{0} <span style=\"font-weight: normal;\">{1}</span>",
                paymentInfo.getPremium(), FontAwesome.RUBLE.getHtml()));
        numberLabel.setValue(invoiceNumber);
    }

    public void initCaptions() {
        Page page = Page.getCurrent();
        WebBrowser webBrowser = page.getWebBrowser();
        boolean isMobile = webBrowser.isWindowsPhone() || webBrowser.isAndroid() || webBrowser.isIOS();

        thanksLabel.setValue(isMobile ? THANKS_MOBILE_VALUE : THANKS_VALUE);

        numberLabel.setCaption(isMobile ? NUMBER_MOBILE_CAPTION : NUMBER_CAPTION);
        summLabel.setCaption(isMobile ? COST_MOBILE_CAPTION : COST_CAPTION);

        String newUrl = page.getLocation().toString();
        Optional<String> successPayment = Stream.of(page.getLocation().toString().split("/"))
                .filter(item -> item.startsWith("SuccessPayment"))
                .findFirst();

        if (successPayment.isPresent()) {
            getLogger().trace("SuccessView current session {}", reflectionToString(VaadinSession.getCurrent()));
            newUrl = StringUtils.replace(page.getLocation().toString(), successPayment.get(), "");
        }

        returnToApplication.setResource(new ExternalResource(newUrl));
    }

    private void buildLayout() {
        setId("payment-panel");
        addStyleName(PayStyles.SUCCESS_PAY_LAYOUT_BACKGROUND);
        setMargin(false);
        setSizeFull();

        setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        addComponent(buildWindow());
    }

    private VerticalLayout buildWindow() {
        VerticalLayout layout = new VerticalLayout();
        layout.setId(LAYOUT_ID);
        layout.setStyleName(PayStyles.BLOCK_BACKGROUND);
        layout.setSizeUndefined();
        layout.setSpacing(true);

        initElements();

        FormLayout paymentData = buildFormLayout(numberLabel, summLabel);

        returnToApplication = new Link();
        returnToApplication.setCaption("Вернуться в Личный кабинет");

        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponents(logo, new HRHtml(), thanksLabel, confirmedLabel, new HRHtml(), paymentData, new HRHtml(), returnToApplication);

        return layout;
    }

    private FormLayout buildFormLayout(Component... components) {
        FormLayout layout = new FormLayout();
        layout.setSizeUndefined();
        layout.setSpacing(false);
        layout.setMargin(false);
        layout.addComponents(components);
        return layout;
    }

    private void initElements() {
        logo = new Image();
        logo.setSource(new ThemeResource(IMG_LOGO_PNG));

        thanksLabel = new Label(THANKS_VALUE, ContentMode.HTML);
        thanksLabel.setStyleName(PayStyles.THANKS_LABEL);

        confirmedLabel = new Label(SUCCESS_VALUE);

        numberLabel = createLabel(NUMBER_CAPTION);
        summLabel = createLabel(COST_CAPTION);
    }

    private Label createLabel(String caption) {
        Label label = new Label();
        label.setCaption(caption);
        label.setContentMode(ContentMode.HTML);

        return label;
    }
}
