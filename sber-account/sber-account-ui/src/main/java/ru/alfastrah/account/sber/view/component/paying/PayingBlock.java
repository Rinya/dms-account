package ru.alfastrah.account.sber.view.component.paying;

import com.vaadin.ui.VerticalLayout;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.styles.view.ContractStyle;

import java.io.Serializable;

abstract class PayingBlock extends VerticalLayout implements Serializable, HasLogger {
    private static final int POLICY_PAYING_TEXT = 10;
    private VerticalLayout contentLayout;
    private VerticalLayout additionalContent;
    UITexts uiTexts;

    PayingBlock(UITexts uiTexts) {
        this.uiTexts = uiTexts;

        init();
    }

    protected int getHeaderTextId() {
        return POLICY_PAYING_TEXT;
    };

    private void init() {
        setId("change-programm-layout");
        setMargin(false);
        setWidth(100, Unit.PERCENTAGE);

        contentLayout = createContentLayout();
        contentLayout.addComponents(uiTexts.getTextComponent(getHeaderTextId()));

        additionalContent = createVerticalLayout("additional-layout");
        addComponents(contentLayout, additionalContent);
    }

    public VerticalLayout getContent() {
        return contentLayout;
    }

    VerticalLayout getAdditionalContent() {
        return additionalContent;
    }

    private VerticalLayout createContentLayout() {
        //VerticalLayout layout = new VerticalLayout();
        VerticalLayout layout = createVerticalLayout("content-layout");
        layout.addStyleName(ContractStyle.CHANGE_PROGRAMM_BLOCK);
        //layout.setSizeFull();
        return layout;
    }

    VerticalLayout createVerticalLayout(String componentId) {
        VerticalLayout layout = new VerticalLayout();
        layout.setId(componentId);
        layout.setSpacing(true);
        layout.setMargin(false);
        layout.setWidth(100, Unit.PERCENTAGE);
        return layout;
    }
}
