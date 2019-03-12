package ru.alfastrah.account.sber.view.login;

import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.RegisterData;
import ru.alfastrah.account.sber.service.LoginService;
import ru.alfastrah.account.sber.styles.view.LoginStyles;

@SpringView(name = PhoneConfirmView.VIEW_NAME, ui = AppUI.class)
@VaadinSessionScope
public class PhoneConfirmView extends LoginView {
    static final String VIEW_NAME = "phone-confirm";

    private LoginService loginService;
    private RegisterData registerData;
    private UITexts uiTexts;

    @Autowired
    public PhoneConfirmView(LoginService loginService, RegisterData registerData, UITexts uiTexts) {
        this.loginService = loginService;
        this.registerData = registerData;
        this.uiTexts = uiTexts;
    }

    @Override
    protected String getViewId() {
        return "phone-confirm-view";
    }

    @Override
    public Component getContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeUndefined();
        layout.setMargin(false);
        Responsive.makeResponsive(layout);
        layout.addStyleName(LoginStyles.LOGIN_PANEL);
        layout.setWidth(50, Unit.PERCENTAGE);

        addFields(layout);

        return layout;
    }

    private void addFields(VerticalLayout layout) {
        Component headLabel = uiTexts.getTextComponent(5);
        headLabel.setId("head-label");

        TextField codeField = new TextField();
        codeField.setId("code-field");
        codeField.setPlaceholder("Код из СМС");

        Button confirmButton = new Button("Подтвердить", (Button.ClickListener) clickEvent -> {
            if (StringUtils.isBlank(codeField.getValue())) {
                Notification.show("Укажите код из смс, пришедший на ваш номер");
                return;
            }

            if (!StringUtils.equals(registerData.getSmsCode(), codeField.getValue())) {
                Notification.show("Указан некорректный код регистрации");
                return;
            }

            try {
                loginService.confirmData();
            } catch (Exception e) {
                getLogger().trace("loginService.confirmData: exception {}", e);
                Notification.show(ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
            }
        });
        confirmButton.setId("confirm-button");
        confirmButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        confirmButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        Component description = uiTexts.getTextComponent(6);
        description.setId("description");

        Label retryLabel = new Label("Повторить отправку на номер (можете исправить номер):");
        retryLabel.setId("retry-label");

        Component resendLayout = buildResendLayout();

        Component footerLabel = uiTexts.getTextComponent(7);
        footerLabel.setId("footer-label");

        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        layout.addComponents(headLabel, codeField, confirmButton, description, retryLabel, resendLayout, footerLabel);
        layout.setComponentAlignment(description, Alignment.MIDDLE_CENTER);
    }

    private Component buildResendLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("resend-layout");
        layout.setMargin(false);
        layout.setSizeUndefined();

        TextField phoneField = new TextField();
        phoneField.setId("phone-field");
        phoneField.setValue(registerData.getPhone());
        phoneField.setPlaceholder("+7 (999) 999-99-99");

        Button sendButton = new Button("Отправить", (Button.ClickListener) clickEvent -> {
            if (StringUtils.isBlank(phoneField.getValue())) {
                Notification.show("Не указан телефон ");
            } else {
                registerData.setPhone(phoneField.getValue());
                loginService.sendSmsCode();
                Notification.show("На ваш номер " + registerData.getPhone() + " отправлено смс с кодом");
            }
        });
        sendButton.setId("send-button");
        sendButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        sendButton.setClickShortcut(ShortcutAction.KeyCode.ENTER, ShortcutAction.ModifierKey.CTRL);

        layout.addComponents(phoneField, sendButton);

        return layout;
    }
}
