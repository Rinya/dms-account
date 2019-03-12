package ru.alfastrah.account.sber.view.component.paying;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.PayingItem;
import ru.alfastrah.account.sber.service.PrintService;
import ru.alfastrah.account.sber.styles.CommonStyles;

import java.util.List;

class PayingGridWithConfirmation extends PayingGrid {
    private static final int CONFIRM_TEXT = 11;
    CheckBox checkBox;

    PayingGridWithConfirmation(PrintService printService, UITexts uiTexts, List<PayingItem> payingItems) {
        super(printService, uiTexts, payingItems);

        getContent().addComponent(createConfirmationLayout());
    }

    private HorizontalLayout createConfirmationLayout() {
        HorizontalLayout checkBoxLayout = new HorizontalLayout();
        checkBoxLayout.setId("check-box-layout");
        checkBoxLayout.setMargin(false);
        checkBoxLayout.setSizeFull();
        checkBoxLayout.addStyleName(CommonStyles.MARGIN_TOP_40);

        checkBox = new CheckBox();
        checkBoxLayout.addComponent(checkBox);
        checkBoxLayout.addComponentsAndExpand(uiTexts.getTextComponent(CONFIRM_TEXT));

        return checkBoxLayout;
    }
}
