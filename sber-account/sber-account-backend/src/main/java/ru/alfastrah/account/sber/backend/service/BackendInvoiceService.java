package ru.alfastrah.account.sber.backend.service;

import ru.alfastrah.account.sber.backend.model.invoice.InvoiceDetail;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceShortData;

import java.io.Serializable;
import java.util.List;

public interface BackendInvoiceService extends Serializable {
    List<InvoiceShortData> getInvoiceList(String login);

    List<InvoiceDetail> getInvoiceDetailList(Long invoiceId);
}
