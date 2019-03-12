package ru.alfastrah.account.sber.backend.model.policy;

public class InvoicePay {
    private Long invoiceId;
    private Long platronId;
    private String payType;


    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getPlatronId() {
        return platronId;
    }

    public void setPlatronId(Long platronId) {
        this.platronId = platronId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "InvoicePay{" +
                "invoiceId=" + invoiceId +
                ", platronId=" + platronId +
                ", payType='" + payType + '\'' +
                '}';
    }
}
