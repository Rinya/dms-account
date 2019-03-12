package ru.alfastrah.account.sber.backend.model;

import java.math.BigDecimal;

public class PaymentData {
    private Long invoiceId;
    private BigDecimal premium;
    private String info;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "PaymentData{" +
                "invoiceId=" + invoiceId +
                ", premium=" + premium +
                ", info='" + info + '\'' +
                '}';
    }
}
