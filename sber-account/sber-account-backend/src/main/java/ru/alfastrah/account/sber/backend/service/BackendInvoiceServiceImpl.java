package ru.alfastrah.account.sber.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.mapper.InvoiceMapper;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceDetail;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceShortData;

import java.util.List;

@Service
public class BackendInvoiceServiceImpl implements BackendInvoiceService {
    private InvoiceMapper invoiceMapper;

    @Autowired
    public BackendInvoiceServiceImpl(InvoiceMapper invoiceMapper) {
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public List<InvoiceShortData> getInvoiceList(String login) {
        return invoiceMapper.getInvoiceList(login);
    }

    @Override
    public List<InvoiceDetail> getInvoiceDetailList(Long invoiceId) {
        return invoiceMapper.getInvoiceDetailList(invoiceId);
    }
}
