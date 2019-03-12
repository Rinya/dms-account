package ru.alfastrah.account.sber.pay;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Label;
import org.apache.commons.lang3.StringUtils;

public class HRHtml extends Label {
    private static final String SEARCHED_STRING = ">";

    /**
     * Creates an empty Label.
     */
    public HRHtml() {
        super("<hr/>");
        setContentMode(ContentMode.HTML);
        setSizeFull();
        setHeight(6, Unit.PIXELS);
    }

    public HRHtml withAlign(String align) {
        setValue(StringUtils.replace(getValue(),
                SEARCHED_STRING, " align=\"" + align + "\""));

        return this;
    }

    public HRHtml withColor(String color) {
        setValue(StringUtils.replace(getValue(),
                SEARCHED_STRING, " color=\"" + color + "\"" + SEARCHED_STRING));

        return this;
    }

    public HRHtml withNoshade() {
        setValue(StringUtils.replace(getValue(),
                SEARCHED_STRING, " noshade" + SEARCHED_STRING));

        return this;
    }

    public HRHtml withSize(int size) {
        if (size != 0) {
            setValue(StringUtils.replace(getValue(),
                    SEARCHED_STRING, " size=\"" + size + "\"" + SEARCHED_STRING));
        }

        return this;
    }

    public HRHtml withWidth(String width) {
        setValue(StringUtils.replace(getValue(), SEARCHED_STRING,
                " width=\"" + width + "\"" + SEARCHED_STRING));

        return this;
    }
}
