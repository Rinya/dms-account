package ru.alfastrah.account.sber.view.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.themes.ValoTheme;
import ru.alfastrah.account.sber.backend.HasLogger;

public abstract class AbstractCollapsedField<T> extends CustomField<T> implements HasLogger {
    protected Button getCollapsedButton() {
        Button button = new Button(getCollapseCaption());
        button.setId("expand-button");
        button.setResponsive(true);
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener(addClickListener());

        return button;
    }

    protected abstract Button.ClickListener addClickListener();

    protected abstract String getCollapseCaption();
}
