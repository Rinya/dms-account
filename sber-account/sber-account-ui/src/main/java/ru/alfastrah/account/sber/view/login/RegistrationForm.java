package ru.alfastrah.account.sber.view.login;

import com.vaadin.data.Binder;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.RegisterData;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.alfastrah.account.sber.util.FieldUtils.*;

@SpringComponent
@VaadinSessionScope
public class RegistrationForm extends FormLayout implements HasLogger {
    private static final String CAPTION_SURNAME = "Фамилия";
    private static final String CAPTION_NAME = "Имя";
    private static final String CAPTION_PHONE = "Моб. телефон";
    private static final String CAPTION_PASS = "Пароль";
    private static final String CAPTION_BIRTHDATE = "Дата рождения";
    private static final String CAPTION_RETRY_PASS = "Повторите пароль";
    private TextField surname;
    private TextField name;
    private TextField patronymic;
    private DateField birthDate;
    private TextField tableNumber;
    private TextField phone;
    private TextField email;
    private PasswordField password;
    private PasswordField retryPassword;

    private Binder<RegisterData> binder;
    private RegisterData registerData;
    private UITexts uiTexts;

    @Autowired
    public RegistrationForm(RegisterData registerData, UITexts uiTexts) {
        this.registerData = registerData;
        this.uiTexts = uiTexts;
    }

    @PostConstruct
    public void init() {
        setId("registration-form");
        setSizeUndefined();
        setMargin(false);

        binder = new Binder<>();
        binder.setBean(registerData);

        buildLayout();

        bindFields();
    }

    Binder<RegisterData> getBinder() {
        return binder;
    }

    boolean isValid() {
        return binder.validate().isOk() && StringUtils.equals(password.getValue(), retryPassword.getValue());
    }

    String getValidateErrorMessage() {
        getLogger().trace("Enter to getValidateErrorMessage");
        String message = binder.validate().getFieldValidationErrors().stream().map(
                error -> error.isError() && error.getMessage().isPresent() ? error.getMessage().get() : "")
                .collect(Collectors.joining("<br>"));

        getLogger().trace("errors message {}", message);

        if (StringUtils.equals(password.getValue(), retryPassword.getValue())) {
            message += "Пароли не совпадают";
        }
        return message;
    }

    private void bindFields() {
        binder.forField(surname)
                .asRequired(getRequiredMessage(CAPTION_SURNAME))
                .withValidator(StringUtils::isNotBlank, getRequiredMessage(CAPTION_SURNAME))
                .bind(RegisterData::getSurname, RegisterData::setSurname);

        binder.forField(name)
                .asRequired(getRequiredMessage(CAPTION_NAME))
                .withValidator(StringUtils::isNotBlank, getRequiredMessage(CAPTION_NAME))
                .bind(RegisterData::getName, RegisterData::setName);

        binder.forField(patronymic)
                .bind(RegisterData::getPatronymic, RegisterData::setPatronymic);

        binder.forField(tableNumber)
                .bind(RegisterData::getTableNumber, RegisterData::setTableNumber);

        binder.forField(phone)
                .asRequired(getRequiredMessage(CAPTION_PHONE))
                .withValidator(StringUtils::isNotBlank, getRequiredMessage(CAPTION_PHONE))
                .bind(RegisterData::getPhone, RegisterData::setPhone);

        binder.forField(email)
                .bind(RegisterData::getEmail, RegisterData::setEmail);

        binder.forField(birthDate)
                .asRequired(getRequiredMessage(CAPTION_BIRTHDATE))
                .withValidator(Objects::nonNull, getRequiredMessage(CAPTION_BIRTHDATE))
                .bind(RegisterData::getBirthDate, RegisterData::setBirthDate);

        binder.forField(password)
                .asRequired(getRequiredMessage(CAPTION_PASS))
                .withValidator(StringUtils::isNotBlank, getRequiredMessage(CAPTION_PASS))
                .bind(RegisterData::getPassword, RegisterData::setPassword);

        binder.forField(retryPassword)
                .asRequired(getRequiredMessage(CAPTION_RETRY_PASS))
                .withValidator(StringUtils::isNotBlank, getRequiredMessage(CAPTION_RETRY_PASS))
                .bind(RegisterData::getRetryPassword, RegisterData::setRetryPassword);
    }

    private void buildLayout() {
        surname = createTextField(CAPTION_SURNAME, "surname", true);
        name = createTextField(CAPTION_NAME, "name", true);
        patronymic = createTextField("Отчество", "patronymic");
        tableNumber = createTextField("Полис/ таб номер", "tableNumber", "Полис/ рабочий таб номер");
        tableNumber.setDescription(uiTexts.getText(1), ContentMode.HTML);
        phone = createTextField(CAPTION_PHONE, "phone", "+7 (999) 999-99-99", true);
        phone.setDescription(uiTexts.getText(2), ContentMode.HTML);
        email = createTextField("E-mail", "email", "aaa@aaa.com");

        birthDate = createDateField(CAPTION_BIRTHDATE, "birthDate", true);

        password = createPasswordField(CAPTION_PASS, "password", "Придумайте пароль", true);
        retryPassword = createPasswordField(CAPTION_RETRY_PASS, "retyr-password", CAPTION_RETRY_PASS, true);
        retryPassword.setDescription(uiTexts.getText(3), ContentMode.HTML);

        addComponents(surname, name, patronymic, birthDate, tableNumber,
                phone, email, password, retryPassword);
    }

    private String getRequiredMessage(String caption) {
        return "Поле " + caption + " должно быть заполнено";
    }
}
