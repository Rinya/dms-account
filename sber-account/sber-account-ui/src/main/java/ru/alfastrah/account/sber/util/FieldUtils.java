package ru.alfastrah.account.sber.util;

import com.vaadin.data.Result;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

public class FieldUtils {
    public static final int COMPONENT_WIDTH = 200;
    private static final String DATE_ERROR_MESSAGE = "Вы ввели некорректную дату";

    public static DateField createDateField(String caption, String id) {
        return createDateField(caption, id, false);
    }

    public static DateField createDateField(String caption, String id, boolean required) {
        DateField field = new DateField(caption) {
            @Override
            protected Result<LocalDate> handleUnparsableDateString(String dateString) {
                Result<LocalDate> localDateResult = super.handleUnparsableDateString(dateString);
                return localDateResult.isError() ? Result.ok(DateUtils.parseDate(dateString)) : localDateResult;
            }
        };
        field.setId(id);
        field.setDateFormat("dd.MM.yyyy");
        field.setPlaceholder("01.01.1900");
        field.setRequiredIndicatorVisible(required);
        field.setWidth(COMPONENT_WIDTH, Sizeable.Unit.PIXELS);
        field.setParseErrorMessage(DATE_ERROR_MESSAGE);
        field.setDateOutOfRangeMessage(DATE_ERROR_MESSAGE);

        return field;
    }

    public static TextField createTextField(String caption, String id) {
        return createTextField(caption, id, null);
    }

    public static TextField createTextField(String caption, String id, String placeholder) {
        return createTextField(caption, id, placeholder, false);
    }

    public static TextField createTextField(String caption, String id, boolean required) {
        return createTextField(caption, id, null, required);
    }

    public static TextField createTextField(String caption, String id, String placeholder, boolean required) {
        TextField field = new TextField(caption);
        field.setPlaceholder(StringUtils.defaultString(placeholder, caption));
        field.setId(id);
        field.setRequiredIndicatorVisible(required);
        field.setResponsive(true);
        field.setWidth(COMPONENT_WIDTH, Sizeable.Unit.PIXELS);

        return field;
    }

    public static PasswordField createPasswordField(String caption, String id, String placeholder, boolean required) {
        PasswordField field = new PasswordField(caption);
        field.setId(id);
        field.setPlaceholder(placeholder);
        field.setRequiredIndicatorVisible(required);
        field.setWidth(COMPONENT_WIDTH, Sizeable.Unit.PIXELS);

        return field;
    }

    public static Label createLabel(String id, String caption, String value) {
        Label label = new Label();
        label.setId(id);
        label.setCaption(caption);
        label.setValue(value);
        label.setResponsive(true);
        return label;
    }
}
