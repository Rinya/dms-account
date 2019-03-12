package ru.alfastrah.account.sber.view.component.paying;

import com.vaadin.data.HasValue;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.PayingItem;
import ru.alfastrah.account.sber.service.PrintService;

import java.util.Collections;

public class PayingForInsured extends PayingGridWithConfirmation {
    public PayingForInsured(PrintService printService, UITexts uiTexts, PayingItem item) {
        super(printService, uiTexts, Collections.singletonList(item));

        setItems(item);
        checkBox.addValueChangeListener((HasValue.ValueChangeListener<Boolean>) valueChangeEvent ->
                sendButton.setEnabled(valueChangeEvent.getValue()));
    }
}
