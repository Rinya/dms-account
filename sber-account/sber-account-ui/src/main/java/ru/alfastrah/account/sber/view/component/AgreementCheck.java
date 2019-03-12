package ru.alfastrah.account.sber.view.component;

import com.vaadin.data.HasValue;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;

import javax.annotation.PostConstruct;

@SpringComponent
@VaadinSessionScope
public class AgreementCheck extends HorizontalLayout {
    private static final String AGREED = "согласен";
    private static final String CONFIRMED = "подтверждаю";
    private static final String IAM = "Я";
    private static final String AND = " и ";
    private static final String AGREEMENT_URL = "https://www.alfastrah.ru/docs/%D0%9F%D0%BE%D0%BB%D0%B8%D1%82%D0%B8%D0%BA%D0%B0%20%D0%BE%D0%B1%D1%80%D0%B0%D0%B1%D0%BE%D1%82%D0%BA%D0%B8%20%D0%BF%D0%B5%D1%80%D1%81%D0%BE%D0%BD%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D1%85%20%D0%B4%D0%B0%D0%BD%D0%BD%D1%8B%D1%85.pdf";
    private CheckBox checkBox;

    @PostConstruct
    public void init() {
        setSizeUndefined();

        buildLayout();
    }

    public void setListener(HasValue.ValueChangeListener<Boolean> listener) {
        checkBox.addValueChangeListener(listener);
    }

    public void setRequiredVisible(boolean visible) {
        checkBox.setRequiredIndicatorVisible(visible);
    }

    private void buildLayout() {
        checkBox = new CheckBox();
        checkBox.setId("agreementCheckBox");

        ExternalResource externalResource = new ExternalResource(AGREEMENT_URL);
        Link agreedLink = new Link(AGREED, externalResource);
        agreedLink.setTargetName("_blank");
        Link confirmLink = new Link(CONFIRMED, externalResource);
        confirmLink.setTargetName("_blank");

        Label iLabel = new Label(IAM);
        Label andLabel = new Label(AND);
        addComponents(checkBox, iLabel, agreedLink, andLabel, confirmLink);
        setComponentAlignment(checkBox, Alignment.MIDDLE_CENTER);
        setComponentAlignment(iLabel, Alignment.MIDDLE_CENTER);
        setComponentAlignment(agreedLink, Alignment.MIDDLE_CENTER);
        setComponentAlignment(andLabel, Alignment.MIDDLE_CENTER);
        setComponentAlignment(confirmLink, Alignment.MIDDLE_CENTER);
    }
}
