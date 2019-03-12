package ru.alfastrah.account.sber.view.component;

import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.model.Insured;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.VzrItem;
import ru.alfastrah.account.sber.styles.view.ContractStyle;
import ru.alfastrah.account.sber.util.DateUtils;
import ru.alfastrah.account.sber.util.FieldUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class VzrForm extends FormLayout {
    private TextField fullName;
    private TextField latinFullName;
    private DateField birthDate;
    private TextField policyDuration;
    private TextField country;

    private InsuredProfile profile;
    private VzrItem vzrItem;
    private Binder<VzrItem> binder;
    private Optional<Insured> insuredPolicy;

    public VzrForm() {
        this(null, Optional.empty());
    }

    public VzrForm(InsuredProfile profile, Optional<Insured> insuredPolicy) {
        this.profile = profile;
        this.vzrItem = new VzrItem();
        this.insuredPolicy = insuredPolicy;

        binder = new Binder<>();
        binder.setBean(vzrItem);

        init();
    }

    public void fillFromProfile() {
        if (profile != null) {
            fullName.setValue(profile.getFullName());
            birthDate.setValue(profile.getBirthDate());
            if (insuredPolicy.isPresent()) {
                Insured insured = insuredPolicy.get();
                policyDuration.setValue("до " + DateUtils.localDateToString(insured.getEndDate()));
                policyDuration.setReadOnly(true);
            }
        }
    }

    public void validate() throws ValidateException {
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

    public VzrItem getItem() {
        return vzrItem;
    }

    private void init() {
        setId("bonus-vzr-layout");
        setMargin(false);
        addStyleName(ContractStyle.POLICY_CARD);

        createFields();
        addComponents(fullName, latinFullName, birthDate, policyDuration, country);
    }

    private void createFields() {
        fullName = FieldUtils.createTextField("ФИО", "fullName", "ФИО", true);
        fullName.setWidth(300, Unit.PIXELS);
        binder.forField(fullName)
                .withValidator(StringUtils::isNotBlank, "Заполните поле ФИО")
                .bind(VzrItem::getFullname, VzrItem::setFullname);

        latinFullName = FieldUtils.createTextField("ФИО латинскими, как в загранпаспорте", "latinFullName",
                "ФИО латинскими, как в загранпаспорте", true);
        latinFullName.setWidth(300, Unit.PIXELS);
        latinFullName.addValueChangeListener((HasValue.ValueChangeListener<String>) valueChangeEvent ->
                latinFullName.setValue(StringUtils.upperCase(valueChangeEvent.getValue())));
        binder.forField(latinFullName)
                .withValidator(StringUtils::isNotBlank, "Заполните поле ФИО латинскими буквами")
                .bind(VzrItem::getLatinFullName, VzrItem::setLatinFullName);

        policyDuration = FieldUtils.createTextField("Срок действия полиса", "fullName",
                "Срок действия полиса", true);
        policyDuration.setWidth(300, Unit.PIXELS);
        policyDuration.setDescription("Совпадает с окончанием полиса ДМС");
        binder.forField(policyDuration)
                .withValidator(StringUtils::isNotBlank, "Заполните поле срок действия полиса")
                .bind(VzrItem::getPolicyDuration, VzrItem::setPolicyDuration);

        country = FieldUtils.createTextField("Укажите страну", "country", "Укажите страну", true);
        country.setWidth(300, Unit.PIXELS);
        binder.forField(country)
                .withValidator(StringUtils::isNotBlank, "Заполните поле укажите страну")
                .bind(VzrItem::getCountry, VzrItem::setCountry);

        birthDate = FieldUtils.createDateField("Дата рождения", "birthDate", true);
        birthDate.setWidth(300, Unit.PIXELS);
        binder.forField(birthDate)
                .withValidator(Objects::nonNull, "Заполните поле дата рождения")
                .bind(VzrItem::getBirthDate, VzrItem::setBirthDate);
    }
}
