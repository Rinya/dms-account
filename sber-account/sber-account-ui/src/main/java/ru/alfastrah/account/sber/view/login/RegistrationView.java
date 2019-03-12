package ru.alfastrah.account.sber.view.login;

import com.vaadin.data.BinderValidationStatusHandler;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.model.RegisterData;
import ru.alfastrah.account.sber.service.LoginService;
import ru.alfastrah.account.sber.styles.view.LoginStyles;
import ru.alfastrah.account.sber.view.component.AgreementCheck;
import ru.alfastrah.account.sber.view.component.greeting.GreetingFranchiseWindow;

import java.util.List;
import java.util.stream.Collectors;

@SpringView(name = RegistrationView.VIEW_NAME, ui = AppUI.class)
@VaadinSessionScope
public class RegistrationView extends LoginView {
    static final String VIEW_NAME = "regitration";

    private RegisterData registerData;

    private Label formStatusLabel;
    private RegistrationForm form;
    private AgreementCheck agreementCheck;
    private GreetingFranchiseWindow greetingWindow;

    private LoginService loginService;

    @Autowired
    public RegistrationView(RegistrationForm form, AgreementCheck agreementCheck,
                            LoginService loginService, RegisterData registerData,
                            GreetingFranchiseWindow greetingWindow) {
        this.form = form;
        this.agreementCheck = agreementCheck;
        this.loginService = loginService;
        this.registerData = registerData;
        this.greetingWindow = greetingWindow;

        formStatusLabel = new Label("", ContentMode.HTML);
        formStatusLabel.setVisible(false);
    }

    @Override
    protected String getViewId() {
        return "registration-view";
    }

    @Override
    public Component getContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeUndefined();
        layout.setMargin(false);
        Responsive.makeResponsive(layout);
        layout.addStyleName(LoginStyles.LOGIN_PANEL);

        HorizontalLayout buttonsLayout = buttonsLayout();

        configureFillStatusLabel();

        layout.addComponents(form, formStatusLabel, agreementCheck, buttonsLayout);
        layout.setComponentAlignment(form, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(formStatusLabel, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(agreementCheck, Alignment.MIDDLE_CENTER);
        layout.setComponentAlignment(buttonsLayout, Alignment.MIDDLE_CENTER);

        return layout;
    }

    private void configureFillStatusLabel() {

        BinderValidationStatusHandler<RegisterData> defaultHandler = form.getBinder().getValidationStatusHandler();
        form.getBinder().setValidationStatusHandler((BinderValidationStatusHandler<RegisterData>) status -> {

            List<BindingValidationStatus<?>> errorList = status.getFieldValidationErrors();

            String errorMessage = errorList.stream().map(validationStatus -> validationStatus.isError() &&
                    validationStatus.getMessage().isPresent() ? validationStatus.getMessage().get() : "")
                    .map(errorString -> "<span style=\"color: red\">" + Jsoup.clean(errorString, Whitelist.simpleText()) + "</span>" )
                    .collect(Collectors.joining("<br>"));

            formStatusLabel.setValue(errorMessage);
            formStatusLabel.setVisible(StringUtils.isNotBlank(errorMessage));

            defaultHandler.statusChange(status);
        });
    }

    private HorizontalLayout buttonsLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("buttonLayout");
        layout.setMargin(false);

        Button register = new Button("Регистрация", (Button.ClickListener) clickEvent -> {
            getLogger().trace("form is valid {}", form.isValid());
            if (form.isValid()) {
                //Пока не показываем, не определились с логикой
                /*if (registerData.getFranchise() != null && registerData.getFranchise() > 0) {
                    getUI().addWindow(greetingWindow);
                    greetingWindow.focus();
                }*/
                loginService.checkUser();
            } else {
                showValidationMessage(form.getValidateErrorMessage());
            }
        });
        register.setId("registerButton");
        register.addStyleName(ValoTheme.BUTTON_PRIMARY);
        register.setEnabled(false);
        register.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        agreementCheck.setListener((HasValue.ValueChangeListener<Boolean>) valueChangeEvent ->
                register.setEnabled(valueChangeEvent.getValue()));
        agreementCheck.setRequiredVisible(true);

        Button cancel = new Button("Отмена", (Button.ClickListener) clickEvent -> loginService.backToAuthorization());
        cancel.setId("cancelButton");
        cancel.setStyleName(LoginStyles.SBER_BUTTON);
        cancel.setClickShortcut(ShortcutAction.KeyCode.ESCAPE);
        layout.addComponents(register, cancel);

        return layout;
    }

    private void showValidationMessage(String errorMessage) {
        Notification notification = new Notification("", Notification.Type.ERROR_MESSAGE);
        notification.setDescription(errorMessage);
        notification.setDelayMsec(2000);
        notification.setHtmlContentAllowed(true);

        notification.show(Page.getCurrent());
    }
}
