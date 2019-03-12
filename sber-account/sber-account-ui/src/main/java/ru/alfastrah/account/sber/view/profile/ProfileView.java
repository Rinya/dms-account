package ru.alfastrah.account.sber.view.profile;

import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.pay.HRHtml;
import ru.alfastrah.account.sber.service.LoginService;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.styles.view.ContractStyle;
import ru.alfastrah.account.sber.styles.view.ProfileStyle;
import ru.alfastrah.account.sber.view.board.MainView;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static ru.alfastrah.account.sber.constants.BoardMenu.PROFILE;

@SpringView(name = ProfileView.VIEW_NAME, ui = AppUI.class)
@UIScope
public class ProfileView extends MainView {
    public static final String VIEW_NAME = "profile";
    private static final int COMPONENT_WIDTH = 200;
    private static final String CAPTION_SURNAME = "Фамилия";
    private static final String CAPTION_NAME = "Имя";
    private static final String CAPTION_PHONE = "Телефон(логин)";
    private static final String CAPTION_BIRTHDATE = "Дата рождения";

    private InsuredProfile profile;
    private UITexts uiTexts;
    private LoginService loginService;

    @Autowired
    public ProfileView(InsuredProfile profile, BoardMenuView menu, UITexts uiTexts, LoginService loginService) {
        super(menu);
        this.profile = profile;
        this.uiTexts = uiTexts;
        this.loginService = loginService;
    }

    @Override
    protected String getViewDescription() {
        return PROFILE.getName();
    }

    @Override
    protected Resource getViewIcon() {
        return PROFILE.getIcon();
    }

    @PostConstruct
    private void buildLayout() {
        getContent().addStyleName(CommonStyles.MAX_WIDTH_500);
        getContent().addComponent(uiTexts.getTextComponent(19));

        VerticalLayout layout = new VerticalLayout();
        layout.setId("profile-layout");
        layout.setMargin(false);
        layout.addStyleName(ContractStyle.POLICY_CARD);
        getContent().addComponent(layout);

        layout.addComponent(getStaticDataLayout());
        if (profile.isCanChangePassword()) {
            HRHtml hr = new HRHtml();
            hr.setWidth(400, Unit.PIXELS);
            layout.addComponent(hr);
            layout.addComponent(getChangingDataLayout());
        }
    }

    private FormLayout getChangingDataLayout() {
        FormLayout formLayout = createFormLayout("changed-layout");
        formLayout.addComponent(changePasswordLayout());

        return formLayout;
    }

    private HorizontalLayout changePasswordLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("change-password-layout");
        layout.setCaption("Пароль");

        PasswordField password = createPasswordField();
        Button button = createChangePasswordButton(password);

        layout.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        layout.addComponents(password, button);

        return layout;
    }

    private Button createChangePasswordButton(PasswordField password) {
        Button button = new Button("изменить");
        button.setId("change-password-button");
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener((Button.ClickListener) clickEvent -> changePassword(password));
        return button;
    }

    private void changePassword(PasswordField password) {
        if (StringUtils.isNotBlank(password.getValue())) {
            loginService.changePassword(profile.getPhone(), password.getValue());
        } else {
            Notification.show("Нельзя сменить пароль на пустое значение", Notification.Type.ERROR_MESSAGE);
        }
    }

    private FormLayout getStaticDataLayout() {
        FormLayout formLayout = createFormLayout("static-layout");
        formLayout.setStyleName(ProfileStyle.PROFILE_FORM);

        Label surname = createLabelField(CAPTION_SURNAME, "surname", profile.getSurname());
        Label name = createLabelField(CAPTION_NAME, "name", profile.getName());
        Label patronymic = createLabelField("Отчество", "patronymic", profile.getPatronymic());
        Label tableNumber = createLabelField("Номер табельный", "tableNumber", profile.getTableNumber());
        Label policyNumber = createLabelField("Номер полиса ДМС", "policyNumber", profile.getPolicyNumber());
        Label phone = createLabelField(CAPTION_PHONE, "phone", profile.getPhone());
        Label email = createLabelField("E-mail", "email", profile.getEmail());
        LocalDate date = profile.getBirthDate();
        Label birthDate = createLabelField(CAPTION_BIRTHDATE, "birthDate",
                date != null ? date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) : "");

        formLayout.addComponents(surname, name, patronymic, birthDate, tableNumber, policyNumber, phone, email);
        return formLayout;
    }

    private FormLayout createFormLayout(String id) {
        FormLayout formLayout = new FormLayout();
        formLayout.setId(id);
        formLayout.setSizeUndefined();
        formLayout.setMargin(true);
        return formLayout;
    }

    private Label createLabelField(String caption, String id, String value) {
        Label label = new Label(value);
        label.setId(id);
        label.setResponsive(true);
        label.setWidth(COMPONENT_WIDTH, Unit.PIXELS);
        label.setCaption(caption);

        return label;
    }

    private PasswordField createPasswordField() {
        PasswordField field = new PasswordField();
        field.setId("password");
        field.setResponsive(true);
        field.setWidth(COMPONENT_WIDTH, Unit.PIXELS);

        return field;
    }
}
