package ru.alfastrah.account.sber.view.component.chat;

import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.styles.view.ContractStyle;
import ru.alfastrah.account.sber.util.DateUtils;

import static ru.alfastrah.account.sber.util.FieldUtils.*;

public class IncorrectRelativeDataBlock extends FormLayout implements HasLogger {
    private InsuredProfile profile;
    private TextField incorrectNameField;
    private TextField nameField;
    private DateField birthDateField;

    IncorrectRelativeDataBlock(InsuredProfile profile) {
        this.profile = profile;

        buildLayout();
    }

    public String getValue() {
        return getFilledTemplate();
    }

    public void validate() throws ValidateException {
        if (StringUtils.isBlank(incorrectNameField.getValue())) {
            throw new ValidateException("Укажите некорректно указанное ФИО родственника");
        }
    }

    private void buildLayout() {
        setId("incorrect-relative-data");
        setMargin(false);
        addStyleName(ContractStyle.POLICY_CARD);

        buildChangingFields();
        buildStaticFields();
    }

    private void buildChangingFields() {
        incorrectNameField = createTextField("Укажите некорректное ФИО (полностью)", "incorrect-name", true);
        nameField = createTextField("Укажите корректное ФИО (полностью)", "correcting-name");
        birthDateField = createDateField("Укажите корректную дату рождения","correcting-birthdate");
        addComponents(incorrectNameField, nameField, birthDateField);
    }

    private void buildStaticFields() {
        Label policyNumberLabel = createLabel("policy-number", "Номер полиса ДМС", profile.getPolicyNumber());
        Label tableNumberLabel = createLabel("table-number", "Табельный номер сотрудника", profile.getTableNumber());
        Label emailLabel = createLabel("e-mail", "Электронный адрес", profile.getEmail());
        Label phoneLabel = createLabel("phone", "Мобильный телефон", profile.getPhone());

        addComponents(policyNumberLabel, tableNumberLabel, emailLabel, phoneLabel);
    }

    private String getFilledTemplate() {
        return "<table border=\"1\" cellpadding=\"10\" style=\"border-collapse: collapse; border: 1px solid #BCBCBC;\">" +
                "<thead><tr style=\"border: 1px solid #FFFFFF; border-bottom: 1px solid #BCBCBC;\">" +
                "<th colspan=\"2\">Данные о родственнике:</th></tr></thead><tbody><tr bordercolor=\"BCBCBC\">" +
                "<td>Некорректное ФИО:</td><td>"+incorrectNameField.getValue()+"</td></tr><tr bordercolor=\"BCBCBC\">" +
                "<td>Корректное ФИО:</td><td>"+nameField.getValue()+"</td></tr><tr bordercolor=\"BCBCBC\">" +
                "<td>Корректная дата рождения:</td><td>"+DateUtils.localDateToString(birthDateField.getValue())+"</td>" +
                "</tr><tr bordercolor=\"BCBCBC\"><td>Номер полиса ДМС:</td><td>"+profile.getPolicyNumber()+"</td>" +
                "</tr><tr bordercolor=\"BCBCBC\"><td>Табельный номер сотрудника:</td><td>"+profile.getTableNumber()+"</td>" +
                "</tr><tr bordercolor=\"BCBCBC\"><td>Электронный адрес:</td><td>"+StringUtils.defaultString(profile.getEmail())+"</td>" +
                "</tr><tr bordercolor=\"BCBCBC\"><td>Мобильный телефон: </td><td>"+StringUtils.defaultString(profile.getPhone())+"</td></tr>" +
                "</tbody></table>";
    }
}
