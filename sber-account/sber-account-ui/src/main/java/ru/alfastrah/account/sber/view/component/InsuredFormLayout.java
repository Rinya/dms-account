package ru.alfastrah.account.sber.view.component;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.Passport;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.styles.view.ContractStyle;

import javax.annotation.PostConstruct;
import java.time.format.DateTimeFormatter;

import static ru.alfastrah.account.sber.util.FieldUtils.createLabel;
import static ru.alfastrah.account.sber.util.FieldUtils.createTextField;

@SpringComponent
@UIScope
public class InsuredFormLayout extends FormLayout {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private static final String EMAIL_FIELD_DESCRIPTION = "Укажите e-mail на который вам будет отправлено сообщение";
    private InsuredProfile profile;
    private PassportField passport;
    private TextField emailField;

    @Autowired
    public InsuredFormLayout(InsuredProfile profile) {
        this.profile = profile;
    }

    public Passport getItem() {
        return passport.getValue();
    }

    public void validate() throws ValidateException {
        passport.validate();

        if (StringUtils.isBlank(emailField.getValue())) {
            throw new ValidateException(EMAIL_FIELD_DESCRIPTION);
        }
    }

    public String getEmail() {
        return emailField.getValue();
    }

    public void collapse() {
        passport.collapse();
    }

    @PostConstruct
    private void buildLayout() {
        setId("insured-form-layout");
        setMargin(false);
        setCaption("Данные о сотруднике:");
        setSpacing(true);
        addStyleName(ContractStyle.POLICY_CARD);
        setSizeUndefined();
        addStyleName(CommonStyles.BLOCK_CAPTION);

        buildStaticFields();

        buildBindedFields();
    }

    private void buildBindedFields() {
        emailField = createTextField("Электронный адрес", "e-mail");
        emailField.setDescription("На данный e-mail вам будет отправлено сообщение");
        emailField.setDescription(EMAIL_FIELD_DESCRIPTION);

        passport = new PassportField(true);
        passport.setResponsive(true);
        passport.setCaption("Паспорт");
        passport.setRequiredIndicatorVisible(true);

        addComponents(emailField, passport);
    }

    private void buildStaticFields() {
        addComponent(createLabel("fullname", "ФИО", profile.getFullName()));
        addComponent(createLabel("birthDate", "Дата рождения", profile.getBirthDate().format(formatter)));
        addComponent(createLabel("policy-number", "Номер полиса ДМС", profile.getPolicyNumber()));
        addComponent(createLabel("table-number", "Табельный номер", profile.getTableNumber()));
        addComponent(createLabel("phone", "Мобильный телефон", profile.getPhone()));
    }
}
