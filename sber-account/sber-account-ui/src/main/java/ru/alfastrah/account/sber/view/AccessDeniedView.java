package ru.alfastrah.account.sber.view;

import com.vaadin.navigator.View;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringView(name = AccessDeniedView.VIEW_NAME)
@VaadinSessionScope
public class AccessDeniedView extends VerticalLayout implements View {
    static final String VIEW_NAME = "accessDenied";

    @PostConstruct
    public void init() {
        setId("access-denied-layout");
        setSizeFull();
        setSpacing(false);
        setMargin(false);

        Label text = new Label("<span style=\"font-size: 250%;\">Access denied</span>");
        text.setContentMode(ContentMode.HTML);
        addComponent(text);
    }
}
