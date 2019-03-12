package ru.alfastrah.account.sber.service;

import com.vaadin.spring.annotation.VaadinSessionScope;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.model.invoice.Invoice;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceDetail;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceShortData;
import ru.alfastrah.account.sber.backend.model.invoice.Invoices;
import ru.alfastrah.account.sber.backend.model.policy.InvoiceCreate;
import ru.alfastrah.account.sber.backend.service.BackendInvoiceService;
import ru.alfastrah.account.sber.backend.service.BackendPayService;
import ru.alfastrah.account.sber.exception.PaymentException;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.InvoiceRow;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@VaadinSessionScope
public class InvoiceServiceImpl implements InvoiceService {
    private static final int INVOICE_PAY_TYPE = 2;
    private BackendInvoiceService invoiceService;
    private BackendPayService backendPayService;

    @Autowired
    public InvoiceServiceImpl(BackendInvoiceService invoiceService, BackendPayService backendPayService) {
        this.invoiceService = invoiceService;
        this.backendPayService = backendPayService;
    }

    @Override
    public List<InvoiceRow> getInvoiceList(String login) {
        List<InvoiceShortData> invoiceList = invoiceService.getInvoiceList(login);
        invoiceList.forEach(item -> getLogger().trace("invoice {}", item));
        return invoiceList
                .stream()
                .map(InvoiceRow::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDetail> getInvoiceDetailList(Long invoiceId) {
        getLogger().trace("Enter to getInvoiceDetailList");
        List<InvoiceDetail> detailList = invoiceService.getInvoiceDetailList(invoiceId);
        detailList.forEach(item -> getLogger().trace("detail {}", item));
        return detailList;
    }


    @Override
    public String paymentInvoices(InsuredProfile profile, List<InvoiceRow> items) throws PaymentException {
        getLogger().trace("Enter to paymentInvoices");

        try {
            InvoiceCreate invoice = fillInvoiceCreate(INVOICE_PAY_TYPE, profile, items);

            Long invoiceId = backendPayService.invoiceCreation(invoice);
            getLogger().trace("created invoice id {}", invoiceId);

            return backendPayService.pay(invoiceId, profile.getPhone(), profile.getEmail());
        } catch (Exception e) {
            getLogger().error("paymentInvoices: profile {} exception {}", profile, e);
            throw new PaymentException(ExceptionUtils.getMessage(e));
        }
    }

    private InvoiceCreate fillInvoiceCreate(int payType, InsuredProfile insured, List<InvoiceRow> items) {
        InvoiceCreate<Invoices> invoice = new InvoiceCreate<>();
        Invoices invoices = new Invoices();
        invoice.setXmlData(invoices);

        BigDecimal totalPremium = BigDecimal.ZERO;
        for (InvoiceRow item: items) {
            invoices.getInvoiceList().add(createInvoice(item));
            totalPremium = totalPremium.add(item.getPayment());
        }

        invoice.setUserId(insured.getUserId());
        invoice.setPolicyId(insured.getPolicyId());
        invoice.setPremium(totalPremium);
        invoice.setPayType(payType);
        return invoice;
    }

    private Invoice createInvoice(InvoiceRow item) {
        Invoice invoice = new Invoice();
        invoice.setId(item.getInvoiceId());
        invoice.setPay(item.getPayment());
        return invoice;
    }
}
