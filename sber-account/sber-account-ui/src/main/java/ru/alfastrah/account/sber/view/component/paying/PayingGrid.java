package ru.alfastrah.account.sber.view.component.paying;

import com.vaadin.data.HasValue;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.PayingItem;
import ru.alfastrah.account.sber.service.PrintService;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.styles.view.ContractStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PayingGrid extends PayingBlock {
    static final String SEND_BUTTON_CAPTION = "Отправить";
    protected List<PayingItem> payingItems;
    protected PayingListener listener;
    private Grid<PayingItem> grid;
    private PrintService printService;
    Button sendButton;

    PayingGrid(PrintService printService, UITexts uiTexts, List<PayingItem> payingItems) {
        super(uiTexts);
        this.printService = printService;
        this.payingItems = payingItems;

        createGrid();
        createButton();

        setItems(payingItems);
    }

    void setItems(PayingItem... items) {
        grid.setItems(items);
    }

    void setItems(List<PayingItem> items) {
        grid.setItems(items);
        int size = CollectionUtils.size(items);
        grid.setHeightByRows(size == 0 ? 1 : size);
    }

    public void setSendButtonListener(Button.ClickListener listener) {
        sendButton.addClickListener(listener);
    }

    protected boolean getCheckBoxValue(PayingItem item) {
        throw new UnsupportedOperationException();
    }

    protected boolean checkBoxIsEnabled(PayingItem item) {
        throw new UnsupportedOperationException();
    }

    protected void changeSendButtonCaptionAndEnabling() {
        List<PayingItem> selectedList = selectedList();
        BigDecimal totalPremium = totalPremium(selectedList);
        sendButton.setCaption(SEND_BUTTON_CAPTION + " " +
                (BigDecimal.ZERO.equals(totalPremium)? "": totalPremium));
        enableSendButton();
    }

    protected void enableSendButton() {
        sendButton.setEnabled(CollectionUtils.isNotEmpty(selectedList()));
    }

    protected List<PayingItem> selectedList() {
        return payingItems.stream()
                .filter(PayingItem::isSelected)
                .collect(Collectors.toList());
    }

    protected BigDecimal totalPremium(List<PayingItem> list) {
        BigDecimal totalPremium = BigDecimal.ZERO;
        for (PayingItem item: list) {
            totalPremium = totalPremium.add(item.getPremium());
        }
        return totalPremium;
    }

    public interface PayingListener extends Serializable {
        void paying(List<PayingItem> list, BigDecimal totalPremium);
    }

    public void setListener(PayingListener listener) {
        this.listener = listener;
    }

    private void createButton() {
        sendButton = createSendButton();

        getAdditionalContent().addComponent(sendButton);
        getAdditionalContent().setComponentAlignment(sendButton, Alignment.MIDDLE_CENTER);
    }

    private Button createSendButton() {
        Button button = new Button(SEND_BUTTON_CAPTION);
        button.setEnabled(false);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addClickListener((Button.ClickListener) clickEvent -> {
            if (listener != null && CollectionUtils.isNotEmpty(payingItems)) {

                List<PayingItem> selectedList = selectedList();
                BigDecimal totalPremium = totalPremium(selectedList);

                listener.paying(selectedList, totalPremium);
            }
        });

        return button;
    }

    private void createGrid() {
        grid = new Grid<>();
        grid.setSizeFull();
        grid.setHeaderVisible(false);
        grid.addStyleName(CommonStyles.GRID);
        grid.setHeightByRows(1);
        getContent().addComponent(grid);

        grid.addComponentColumn(item -> {
            CheckBox selected = new CheckBox();
            selected.addValueChangeListener((HasValue.ValueChangeListener<Boolean>) valueChangeEvent -> {
                item.setSelected(valueChangeEvent.getValue());
                changeSendButtonCaptionAndEnabling();
            });
            selected.setValue(getCheckBoxValue(item));
            selected.setEnabled(checkBoxIsEnabled(item));

            return selected;
        }).setWidthUndefined();

        grid.addColumn(item -> item.getFullName() + " (" + item.getBirthDate()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")")
                .setExpandRatio(1);
        grid.addColumn(item -> item.getPremium() + " Р");
        grid.addComponentColumn(item -> {
            Image pdf = new Image();
            pdf.setId("programm-condition-image");
            pdf.addStyleName(ContractStyle.PDF_BUTTON);
            pdf.setWidth(50, Unit.PIXELS);
            try {
                StreamResource policyPdf = printService.getPdf(item.getPolicyId(), item.getTemplateId());
                BrowserWindowOpener policyOpener = new BrowserWindowOpener(policyPdf);
                policyOpener.extend(pdf);
            } catch (Exception e) {
                getLogger().error("printService.getChangeProgrammPdf on policyId {} exception", item.getPolicyId(), e);
                Notification.show(ExceptionUtils.getMessage(e), Notification.Type.ERROR_MESSAGE);
            }

            return pdf;
        });
        grid.addColumn(item -> "программа").setWidthUndefined();
    }
}
