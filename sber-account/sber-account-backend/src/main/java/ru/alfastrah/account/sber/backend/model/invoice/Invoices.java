package ru.alfastrah.account.sber.backend.model.invoice;

import org.apache.commons.collections4.CollectionUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement(name = "Invoices")
@XmlAccessorType(XmlAccessType.NONE)
public class Invoices {
    @XmlElement(name = "Invoice")
    private List<Invoice> invoiceList;

    public List<Invoice> getInvoiceList() {
        if (CollectionUtils.isEmpty(invoiceList)) {
            invoiceList = new LinkedList<>();
        }
        return invoiceList;
    }

    public void setInvoiceList(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    @Override
    public String toString() {
        return "Invoices{" +
                "invoiceList=" + invoiceList +
                '}';
    }
}
