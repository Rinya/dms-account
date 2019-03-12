package ru.alfastrah.account.sber.view.board;

import com.vaadin.data.HasValue;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.exception.PaymentException;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.InvoiceRow;
import ru.alfastrah.account.sber.service.InvoiceService;
import ru.alfastrah.account.sber.styles.InvoiceStyle;
import ru.alfastrah.account.sber.view.component.InvoiceWindow;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static ru.alfastrah.account.sber.constants.BoardMenu.INVOICE;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showErrorNotification;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showSuccessNotification;

@SpringView(name = InvoiceView.VIEW_NAME, ui = AppUI.class)
@UIScope
public class InvoiceView extends MainView {
    public static final String VIEW_NAME = "invoices";
    private static final int DEPOSIT_TEXT = 13;
    private static final int USER_TEXT = 12;
    private static final int PAY_TEXT = 14;
    private static final int DESCRIPTION_TEXT = 15;
    public static final String PAY_BUTTON_CAPTION_TEMPLATE = "ОПЛАТИТЬ %s Р";
    private InvoiceService invoiceService;
    private UITexts uiTexts;
    private Grid<InvoiceRow> grid;
    private InsuredProfile profile;
    private InvoiceWindow invoiceWindow;
    private Button payButton;

    private List<InvoiceRow> invoiceList;

    @Autowired
    public InvoiceView(BoardMenuView menu, InvoiceService invoiceService, InsuredProfile profile,
                       InvoiceWindow invoiceWindow, UITexts uiTexts) {
        super(menu);
        this.invoiceService = invoiceService;
        this.uiTexts = uiTexts;
        this.profile = profile;
        this.invoiceWindow = invoiceWindow;
    }

    @Override
    protected String getViewDescription() {
        return INVOICE.getName();
    }

    @Override
    protected Resource getViewIcon() {
        return INVOICE.getIcon();
    }

    @PostConstruct
    private void buildLayout() {
        getContent().removeAllComponents();
        invoiceList = invoiceService.getInvoiceList(profile.getPhone());
        getLogger().trace("invoiceView list size {}", invoiceList.size());

        getContent().addComponent(createInfoLayout(CollectionUtils.isNotEmpty(invoiceList)));
        if (CollectionUtils.isNotEmpty(invoiceList)) {
            grid = createGrid();
            getContent().addComponent(grid);
            getContent().addComponent(createPayLayout());
            getContent().addComponent(descriptionInfo());

            setItems(invoiceList);
        }
    }

    private HorizontalLayout createInfoLayout(boolean showDeposit) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("info-layout");
        layout.setSizeFull();

        Component userInfo = uiTexts.getTextComponent(USER_TEXT);
        layout.addComponent(userInfo);
        layout.setExpandRatio(userInfo, 0.8f);

        if (showDeposit) {
            Component depositInfo = uiTexts.getTextComponent(DEPOSIT_TEXT);
            layout.addComponent(depositInfo);
            layout.setExpandRatio(depositInfo, 0.3f);
        }

        return layout;
    }

    private void setItems(List<InvoiceRow> invoiceList) {
        grid.setItems(invoiceList);
        int size = CollectionUtils.size(invoiceList);
        grid.setHeightByRows(size == 0 ? 1 : size);
    }

    private Grid<InvoiceRow> createGrid() {
        Grid<InvoiceRow> invoiceRowGrid = new Grid<>();
        invoiceRowGrid.setSizeFull();
        invoiceRowGrid.setHeightByRows(1);

        invoiceRowGrid.addComponentColumn(item -> {
            CheckBox selected = new CheckBox();
            selected.setValue(item.isSelected());
            selected.addValueChangeListener((HasValue.ValueChangeListener<Boolean>) valueChangeEvent -> {
                item.setSelected(valueChangeEvent.getValue());
                enablePayButtonAndChangeCaption();
            });

            return selected;
        }).setCaption("ВЫБРАТЬ");

        invoiceRowGrid.addColumn(InvoiceRow::getInvoiceNumber).setCaption("НОМЕР СЧЕТА");

        invoiceRowGrid.addComponentColumn(item -> {
            Image invoiceImage = new Image();
            invoiceImage.setId("invoice-image");
            invoiceImage.addStyleName(InvoiceStyle.INVOICE_BUTTON);
            invoiceImage.setWidth(50, Unit.PIXELS);
            invoiceImage.addClickListener((MouseEvents.ClickListener) clickEvent ->
                    showInvoiceDetailWindow(getUI(), item));

            return invoiceImage;
        }).setCaption("СЧЕТ");

        invoiceRowGrid.addColumn(item -> item.getIssueDate()
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
                .setCaption("ДАТА ФОРМИРОВАНИЯ");
        invoiceRowGrid.addColumn(InvoiceRow::getInsured).setCaption("ЗАСТРАХОВАННЫЙ");
        invoiceRowGrid.addColumn(InvoiceRow::getAccount).setCaption("Категория");
        invoiceRowGrid.addColumn(InvoiceRow::getPremium).setCaption("СУММА СООПЛАТЫ");
        invoiceRowGrid.addColumn(InvoiceRow::getDeposit).setCaption("Гарантийный взнос");
        invoiceRowGrid.addColumn(InvoiceRow::getPayment).setCaption("ИТОГО К ОПЛАТЕ");
        invoiceRowGrid.addColumn(InvoiceRow::getInvoiceState).setCaption("СТАТУС");

        return invoiceRowGrid;
    }

    private void enablePayButtonAndChangeCaption() {
        List<InvoiceRow> selectedList = selectedInvoiceList();
        payButton.setEnabled(CollectionUtils.isNotEmpty(selectedList));
        payButton.setCaption(String.format(PAY_BUTTON_CAPTION_TEMPLATE, totalPremium(selectedList)));
    }

    private BigDecimal totalPremium(List<InvoiceRow> list) {
        BigDecimal totalPremium = BigDecimal.ZERO;
        for (InvoiceRow item: list) {
            totalPremium = totalPremium.add(item.getPayment());
        }

        totalPremium = totalPremium.setScale(2, RoundingMode.HALF_UP);
        return totalPremium;
    }

    private HorizontalLayout createPayLayout() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("pay-layout");
        layout.setSizeFull();

        Component payInfo = uiTexts.getTextComponent(PAY_TEXT);

        payButton = new Button(String.format(PAY_BUTTON_CAPTION_TEMPLATE, 0));
        payButton.setEnabled(false);
        payButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
        payButton.addClickListener((Button.ClickListener) clickEvent -> payingInvoice());

        layout.addComponents(payInfo, payButton);
        layout.setExpandRatio(payInfo, 0.8f);

        return layout;
    }

    private Component descriptionInfo() {
        Component info = uiTexts.getTextComponent(DESCRIPTION_TEXT);
        info.setWidth(80, Unit.PERCENTAGE);
        return info;
    }

    private void showInvoiceDetailWindow(UI currentUI, InvoiceRow invoiceRow) {
        getLogger().trace("Enter to showInvoiceDetailWindow");
        currentUI.addWindow(invoiceWindow);
        invoiceWindow.mountData(invoiceRow);
        invoiceWindow.setCaption("СЧЕТ № " + invoiceRow.getInvoiceNumber());

        invoiceWindow.focus();
    }

    private List<InvoiceRow> selectedInvoiceList() {
        return invoiceList.parallelStream()
                .filter(InvoiceRow::isSelected)
                .collect(Collectors.toList());
    }

    private void payingInvoice() {
        getLogger().trace("Enter to payingInvoice");
        try {
            String redirectUrl = invoiceService.paymentInvoices(profile, selectedInvoiceList());
            buildLayout();

            getUI().getPage().open(redirectUrl, "_target");
            showSuccessNotification("Оплата выбранных счетов успешно выполнено");
        } catch (PaymentException e) {
            getLogger().error("invoiceService.paymentInvoices: exception {}", e);
            showErrorNotification("Не удалось оплатить выбранные счета ", e);
        }
    }
}
