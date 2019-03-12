package ru.alfastrah.account.sber.view.login;

import com.vaadin.data.HasValue;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Responsive;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.model.LoginUser;
import ru.alfastrah.account.sber.service.LoginService;
import ru.alfastrah.account.sber.styles.view.LoginStyles;

import static ru.alfastrah.account.sber.styles.view.LoginStyles.BOLD;
import static ru.alfastrah.account.sber.styles.view.LoginStyles.WELCOME_LABEL;

@SpringView(name = AuthorizationView.VIEW_NAME, ui = AppUI.class)
@VaadinSessionScope
public class AuthorizationView extends LoginView implements HasValue.ValueChangeListener<String> {
    static final String VIEW_NAME = "authorization";
    private static final String REMEMBER_ME = "Запомнить меня";
    private static final String REMEMBER_ME_ID = "remember_me_id";
    private static final String USERNAME_CAPTION = "Номер телефона";
    private static final String ENTER_LABEL_MESSAGE = "Вход в личный кабинет";
    private static final String PASSW = "Пароль";
    private static final String SIGN_IN = "ВОЙТИ";
    private static final String REGISTRATION = "Регистрация";

    private CheckBox checkBox;
    private Button signing;
    private PasswordField password;
    private TextField loginField;

    private LoginService loginService;

    @Autowired
    public AuthorizationView(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected String getViewId() {
        return "authorization-view";
    }

    @Override
    public Component getContent() {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeUndefined();
        layout.setMargin(false);
        Responsive.makeResponsive(layout);
        layout.addStyleName(LoginStyles.LOGIN_PANEL);

        layout.addComponent(buildInfo());
        layout.addComponent(buildFields());
        layout.addComponent(buildServiceLayer());

        return layout;
    }

    @Override
    public void valueChange(HasValue.ValueChangeEvent<String> event) {
        signing.setEnabled(StringUtils.isNotBlank(password.getValue()) && StringUtils.isNotBlank(loginField.getValue()));
    }

    private Component buildInfo() {
        CssLayout labels = new CssLayout();
        labels.addStyleName(LoginStyles.LABELS);

        Label welcome = new Label(ENTER_LABEL_MESSAGE);
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H4);
        welcome.addStyleName(WELCOME_LABEL);
        welcome.addStyleName(BOLD);
        labels.addComponent(welcome);

        return labels;
    }

    private Component buildFields() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.addStyleName(LoginStyles.FIELDS);

        //TODO очищается поле, после возврата с предыдущего окна
        loginField = new TextField(USERNAME_CAPTION);
        loginField.setId("id_user_name");
        loginField.setIcon(VaadinIcons.USER);
        loginField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        loginField.addValueChangeListener(this);
        loginField.setRequiredIndicatorVisible(true);
        loginField.focus();

        password = new PasswordField(PASSW);
        password.setId("id_user_password");
        password.setIcon(VaadinIcons.LOCK);
        password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        password.setRequiredIndicatorVisible(true);
        password.addValueChangeListener(this);

        signing = new Button(SIGN_IN);
        signing.setId("id_signin");
        signing.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signing.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        signing.setEnabled(false);
        signing.setDisableOnClick(true);

        fields.addComponents(loginField, password, signing);
        fields.setComponentAlignment(signing, Alignment.BOTTOM_LEFT);

        signing.addClickListener((Button.ClickListener) event -> {
            loginService.authorize(createLoginUser());
            signing.setEnabled(true);
        });
        return fields;
    }

    private LoginUser createLoginUser() {
        LoginUser loginUser = new LoginUser();
        loginUser.setLogin(loginField.getValue());
        loginUser.setPassword(password.getValue());
        loginUser.setRememberMe(checkBox.getValue());
        return loginUser;
    }

    private Component buildServiceLayer() {
        HorizontalLayout fields = new HorizontalLayout();
        fields.setSizeFull();
        fields.setMargin(false);

        checkBox = new CheckBox(REMEMBER_ME, true);
        checkBox.setId(REMEMBER_ME_ID);
        checkBox.addStyleName(LoginStyles.REMEMBER_ME);

        Button registration = new Button(REGISTRATION, (Button.ClickListener) clickEvent ->
                loginService.registerUser(loginField.getValue(), checkBox.getValue()));
        registration.setStyleName(ValoTheme.BUTTON_LINK);

        fields.addComponents(checkBox, registration);
        fields.setComponentAlignment(checkBox, Alignment.MIDDLE_LEFT);
        fields.setComponentAlignment(registration, Alignment.MIDDLE_RIGHT);

        return fields;
    }
}
