package ru.alfastrah.account.sber.view.login;

import com.vaadin.navigator.View;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.styles.view.LoginStyles;

import javax.annotation.PostConstruct;

public abstract class LoginView extends VerticalLayout implements View, HasLogger {
    @PostConstruct
    public void init() {
        Responsive.makeResponsive(this);
        getLogger().info("Enter to {}", getViewId());

        setId(getViewId());
        addStyleName(LoginStyles.LOGIN_VIEW_IMAGE);
        setSpacing(false);
        setMargin(false);
        setWidth(100, Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);

        Component content = getContent();
        addComponent(content);
        setComponentAlignment(content, Alignment.MIDDLE_CENTER);
    }

    protected abstract String getViewId();

    public abstract Component getContent();
}
