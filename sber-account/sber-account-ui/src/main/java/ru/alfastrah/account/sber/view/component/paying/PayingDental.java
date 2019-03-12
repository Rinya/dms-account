package ru.alfastrah.account.sber.view.component.paying;

import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.PayingItem;
import ru.alfastrah.account.sber.service.PrintService;

import java.util.List;

public class PayingDental extends PayingGrid {
    private static final int PAYING_TEXT_ID = 24;
    private static final int CONFIRM_TEXT_ID = 23;

    public PayingDental(PrintService printService, UITexts uiTexts, List<PayingItem> payingItems) {
        super(printService, uiTexts, payingItems);

        getContent().addComponent(uiTexts.getTextComponent(CONFIRM_TEXT_ID));
    }

    public void refreshList(List<PayingItem> payingItems) {
        setItems(payingItems);
    }

    @Override
    protected int getHeaderTextId() {
        return PAYING_TEXT_ID;
    }

    @Override
    protected boolean getCheckBoxValue(PayingItem item) {
        return item.isSelected();
    }

    @Override
    protected boolean checkBoxIsEnabled(PayingItem item) {
        return true;
    }
}
