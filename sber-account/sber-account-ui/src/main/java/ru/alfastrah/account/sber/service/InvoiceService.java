package ru.alfastrah.account.sber.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceDetail;
import ru.alfastrah.account.sber.exception.PaymentException;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.InvoiceRow;

import java.io.Serializable;
import java.util.List;


public interface InvoiceService extends Serializable, HasLogger {
    List<InvoiceRow> getInvoiceList(String login);

    List<InvoiceDetail> getInvoiceDetailList(Long invoiceId);

    String paymentInvoices(InsuredProfile profile, List<InvoiceRow> items) throws PaymentException;
}
