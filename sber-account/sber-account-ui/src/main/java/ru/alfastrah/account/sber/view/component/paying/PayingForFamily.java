package ru.alfastrah.account.sber.view.component.paying;

import com.vaadin.data.HasValue;
import org.apache.commons.collections4.CollectionUtils;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.PayingItem;
import ru.alfastrah.account.sber.service.PrintService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class PayingForFamily extends PayingGridWithConfirmation {
    public PayingForFamily(PrintService printService, UITexts uiTexts, List<PayingItem> payingItems) {
        super(printService, uiTexts, payingItems);

        setItems(payingItems);
        checkBox.addValueChangeListener((HasValue.ValueChangeListener<Boolean>) valueChangeEvent -> enableSendButton());
    }

    public boolean userSelected() {
        return payingItems.parallelStream().anyMatch(item -> item.isUser() && item.isSelected());
    }

    @Override
    protected boolean getCheckBoxValue(PayingItem item) {
        return item.isSelected() || item.isUser();
    }

    @Override
    protected boolean checkBoxIsEnabled(PayingItem item) {
        return !item.isUser() && item.isCanChangeProgramm();
    }

    @Override
    protected void changeSendButtonCaptionAndEnabling() {
        List<PayingItem> selectedList = selectedList();
        BigDecimal totalPremium = totalPremium(selectedList);
        sendButton.setCaption(SEND_BUTTON_CAPTION + " " +
                (BigDecimal.ZERO.equals(totalPremium)? "": totalPremium));
        enableSendButton();
    }

    @Override
    protected List<PayingItem> selectedList() {
        return payingItems.stream().filter(item -> item.isSelected() && !item.isUser())
                .collect(Collectors.toList());
    }

    @Override
    protected void enableSendButton() {
        sendButton.setEnabled(checkBox.getValue() && (CollectionUtils.isNotEmpty(selectedList()) || userSelected()));
    }
}
