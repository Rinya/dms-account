package ru.alfastrah.account.sber.view.component;

import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.model.Passport;

import java.util.Objects;
import java.util.stream.Collectors;

import static ru.alfastrah.account.sber.util.FieldUtils.createDateField;
import static ru.alfastrah.account.sber.util.FieldUtils.createTextField;

public class PassportField extends AbstractCollapsedField<Passport> {
    private static final String FILL_PASSPORT_MESSAGE = "Заполните паспортные данные";
    private Passport passport;
    private Binder<Passport> binder;
    private VerticalLayout passportLayout;
    private Button expandButton;
    private TextField seriaField;
    private TextField numberField;
    private TextField issuePlaceField;
    private DateField issueDateField;
    private boolean withValidating;

    public PassportField() {
        this(false);
    }

    PassportField(boolean withValidating) {
        this.withValidating = withValidating;

        passport = new Passport();
        binder = new Binder<>();
        binder.setBean(passport);
    }

    @Override
    protected Component initContent() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setId("passport-component-layout");
        mainLayout.setMargin(false);
        mainLayout.setSpacing(false);

        passportLayout = createPassportLayout();
        expandButton = getCollapsedButton();

        mainLayout.addComponent(expandButton);
        mainLayout.addComponent(passportLayout);

        return mainLayout;
    }

    @Override
    protected void doSetValue(Passport passport) {
        this.passport = passport;
        binder.setBean(passport);
        changeButtonCaption(passport);
    }

    @Override
    public Passport getValue() {
        return passport;
    }

    @Override
    protected Button.ClickListener addClickListener() {
        return clickEvent -> collapseField(!passportLayout.isVisible());
    }

    @Override
    protected String getCollapseCaption() {
        return FILL_PASSPORT_MESSAGE;
    }

    void collapse() {
        collapseField(false);
    }

    void validate() throws ValidateException {
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

    private void collapseField(boolean visible) {
        passportLayout.setVisible(visible);
        changeButtonCaption(passport);
    }

    private void changeButtonCaption(Passport passport) {
        if (expandButton != null) {
            expandButton.setCaption(passport != null && !passport.isEmpty() ? passport.getPassport() : FILL_PASSPORT_MESSAGE);
        }
    }

    private VerticalLayout createPassportLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("passport-layout");
        layout.setMargin(false);
        layout.setResponsive(true);
        layout.setVisible(false);

        seriaField = createTextField(null, "passport-seria", "Паспорт серия", isRequiredIndicatorVisible());
        binder.forField(seriaField).bind(Passport::getSeria, Passport::setSeria);

        numberField = createTextField(null, "passport-number", "Паспорт номер", isRequiredIndicatorVisible());
        binder.forField(numberField).bind(Passport::getNumber, Passport::setNumber);

        issuePlaceField = createTextField(null, "passport-placeissue", "Кем выдан", isRequiredIndicatorVisible());
        binder.forField(issuePlaceField).bind(Passport::getPlaceIssue, Passport::setPlaceIssue);

        issueDateField = createDateField(null, "passport-dateissue", isRequiredIndicatorVisible());
        binder.forField(issueDateField).bind(Passport::getDateIssue, Passport::setDateIssue);

        if (withValidating) {
            binder.forField(seriaField)
                    .withValidator(StringUtils::isNotBlank, "Укажите серию паспорта")
                    .bind(Passport::getSeria, Passport::setSeria);

            binder.forField(numberField)
                    .withValidator(StringUtils::isNotBlank, "Укажите номер паспорта")
                    .bind(Passport::getNumber, Passport::setNumber);

            binder.forField(issuePlaceField)
                    .withValidator(StringUtils::isNotBlank, "Заполните поле кем выдан")
                    .bind(Passport::getPlaceIssue, Passport::setPlaceIssue);

            binder.forField(issueDateField)
                    .withValidator(Objects::nonNull, "Заполните поле дата выдачи")
                    .bind(Passport::getDateIssue, Passport::setDateIssue);
        }

        layout.addComponents(seriaField, numberField, issuePlaceField, issueDateField);

        return layout;
    }
}
