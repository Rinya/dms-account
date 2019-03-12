package ru.alfastrah.account.sber.view.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.themes.ValoTheme;

public interface CollapsedField {
    default Button getCollapsedButton() {
        Button button = new Button(getCollapseCaption());
        button.setId("expand-button");
        button.setResponsive(true);
        button.addStyleName(ValoTheme.BUTTON_LINK);
        button.addClickListener(addClickListener());

        return button;
    }

    Button.ClickListener addClickListener();

    String getCollapseCaption();
}
