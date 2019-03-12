package ru.alfastrah.account.sber.backend.model.invoice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alfastrah.account.sber.backend.TestBackendConfiguration;
import ru.alfastrah.account.sber.backend.converter.XmlConverter;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestBackendConfiguration.class})
public class InvoicesTest {
    @Autowired
    private XmlConverter converter;

    @Test
    public void testInvoices() {
        Invoices invoices = new Invoices();
        Invoice invoice = new Invoice();
        invoice.setId(1L);
        invoice.setPay(BigDecimal.valueOf(400));
        invoices.getInvoiceList().add(invoice);

        invoice = new Invoice();
        invoice.setId(2L);
        invoice.setPay(BigDecimal.valueOf(600));
        invoices.getInvoiceList().add(invoice);

        System.out.println(converter.doMarshaling(invoices));
    }
}