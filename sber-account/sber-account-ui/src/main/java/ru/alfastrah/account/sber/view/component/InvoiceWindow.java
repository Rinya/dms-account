package ru.alfastrah.account.sber.view.component;

import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.model.InvoiceRow;
import ru.alfastrah.account.sber.service.InvoiceService;

import javax.annotation.PostConstruct;

@SpringComponent
@VaadinSessionScope
public class InvoiceWindow extends Window implements HasLogger {
    private InvoiceService invoiceService;
    private InvoiceDetailLayout detailLayout;

    @Autowired
    public InvoiceWindow(InvoiceService invoiceService, InvoiceDetailLayout detailLayout) {
        this.invoiceService = invoiceService;
        this.detailLayout = detailLayout;

        buildLayout();
    }

    private void buildLayout() {
        setId("greeting-window");
        setCaptionAsHtml(true);
        setResizable(false);
        setModal(true);
        setWindowMode(WindowMode.NORMAL);
        Responsive.makeResponsive(this);
        setWidth(50, Unit.PERCENTAGE);

        center();
    }

    @PostConstruct
    private void init() {
        setContent(detailLayout);
    }

    public void mountData(InvoiceRow invoiceRow) {
        detailLayout.setItems(invoiceService.getInvoiceDetailList(invoiceRow.getInvoiceId()));
        detailLayout.setInvoice(invoiceRow.getInvoiceNumber());
    }
}
