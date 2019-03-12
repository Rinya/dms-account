package ru.alfastrah.account.sber.view.component;

import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.constants.Gender;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.model.InsuredRelative;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.styles.view.ContractStyle;
import ru.alfastrah.account.sber.util.FieldUtils;

import java.util.Objects;
import java.util.stream.Collectors;

import static ru.alfastrah.account.sber.util.FieldUtils.createDateField;
import static ru.alfastrah.account.sber.util.FieldUtils.createTextField;

public class InsuredRelativeLayout extends FormLayout {
    private InsuredRelative insuredRelative;
    private Binder<InsuredRelative> binder;
    private AddressField address;
    private PassportField passport;

    public InsuredRelativeLayout() {
        this.insuredRelative = new InsuredRelative();

        binder = new Binder<>();
        binder.setBean(insuredRelative);

        buildLayout();
    }

    public InsuredRelative getItem() {
        return insuredRelative;
    }

    public void validate() throws ValidateException {
        passport.validate();
        address.validate();

        if (!binder.validate().isOk()) {
            String errorMessage = binder
                    .validate()
                    .getFieldValidationErrors()
                    .stream()
                    .filter(BindingValidationStatus::isError)
                    .map(BindingValidationStatus::getMessage)
                    .map(item -> item.orElse(""))
                    .collect(Collectors.joining("<br>"));
            throw new ValidateException(errorMessage);
        }
    }

    public void collapse() {
        passport.collapse();
        address.collapse();
    }

    private void buildLayout() {
        setId("insured-relative-layout");
        setMargin(false);
        setCaption("Данные о родственниках:");
        setSpacing(true);
        addStyleName(ContractStyle.POLICY_CARD);
        addStyleName(CommonStyles.BLOCK_CAPTION);
        setSizeUndefined();

        buildBindedFields();
    }

    private void buildBindedFields() {
        TextField surnameField = createTextField("Фамилия родственника", "surname");
        binder
                .forField(surnameField)
                .withValidator(StringUtils::isNotBlank, "Укажите фамилия родственника")
                .bind(InsuredRelative::getSurname, InsuredRelative::setSurname);

        TextField nameField = createTextField("Имя родственника", "name");
        binder
                .forField(nameField)
                .withValidator(StringUtils::isNotBlank, "Укажите имя родственника")
                .bind(InsuredRelative::getName, InsuredRelative::setName);

        TextField patronymicField = createTextField("Отчество родственника", "patronymic", false);
        binder
                .forField(patronymicField)
                .bind(InsuredRelative::getPatronymic, InsuredRelative::setPatronymic);

        ComboBox<Gender> genderFiled = createComboBox();
        binder
                .forField(genderFiled)
                .withValidator(Objects::nonNull, "Укажите пол родственника")
                .bind(InsuredRelative::getGender, InsuredRelative::setGender);

        passport = new PassportField(true);
        passport.setResponsive(true);
        passport.setRequiredIndicatorVisible(true);
        passport.setCaption("Паспорт");

        binder.forField(passport)
                .bind(InsuredRelative::getPassport, InsuredRelative::setPassport);

        TextField degreeField = createTextField("Степень родства", "degree");
        binder
                .forField(degreeField)
                .withValidator(StringUtils::isNotBlank, "Укажите степень родства")
                .bind(InsuredRelative::getDegree, InsuredRelative::setDegree);

        DateField birthDateField = createDateField("Дата рождения родственника", "birthDate", true);
        binder
                .forField(birthDateField)
                .withValidator(Objects::nonNull, "Заполните поле дата рождения родственника")
                .bind(InsuredRelative::getBirthDate, InsuredRelative::setBirthDate);

        TextField phoneField = createTextField("Контактный номер телефона родственника", "phone");
        binder
                .forField(phoneField)
                .withValidator(StringUtils::isNotBlank, "Укажите контактный номер")
                .bind(InsuredRelative::getPhone, InsuredRelative::setPhone);

        TextField attachCityField = createTextField("Город прикрепления по программе ДМС", "attachCity");
        binder
                .forField(attachCityField)
                .withValidator(StringUtils::isNotBlank, "Укажите город прикрепления")
                .bind(InsuredRelative::getAttachCity, InsuredRelative::setAttachCity);

        address = new AddressField();
        address.setResponsive(true);
        address.setRequiredIndicatorVisible(true);
        address.setCaption("Адрес фактического проживания родственника");
        binder.forField(address)
                .bind(InsuredRelative::getAddress, InsuredRelative::setAddress);

        addComponents(surnameField, nameField, patronymicField, genderFiled, passport,
                degreeField, birthDateField, address, phoneField, attachCityField);
    }

    private ComboBox<Gender> createComboBox() {
        ComboBox<Gender> genderFiled = new ComboBox<>();
        genderFiled.setId("gender");
        genderFiled.setWidth(FieldUtils.COMPONENT_WIDTH, Unit.PIXELS);
        genderFiled.setItems(Gender.values());
        genderFiled.setEmptySelectionAllowed(false);
        genderFiled.setItemCaptionGenerator((ItemCaptionGenerator<Gender>) Gender::getName);
        genderFiled.setCaption("Пол родственника");
        genderFiled.setRequiredIndicatorVisible(true);
        genderFiled.setResponsive(true);
        return genderFiled;
    }
}
