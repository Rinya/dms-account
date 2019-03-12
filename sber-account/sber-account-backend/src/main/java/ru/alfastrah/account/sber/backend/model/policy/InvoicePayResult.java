package ru.alfastrah.account.sber.backend.model.policy;

public class InvoicePayResult {
    private InvoicePay invoice;
    private String success;
    private String faultMessage;

    public InvoicePayResult(InvoicePay invoice) {
        this.invoice = invoice;
    }

    public Long getInvoiceId() {
        return invoice.getInvoiceId();
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoice.setInvoiceId(invoiceId);
    }

    public Long getPlatronId() {
        return invoice.getPlatronId();
    }

    public void setPlatronId(Long platronId) {
        this.invoice.setPlatronId(platronId);
    }

    public String getPayType() {
        return invoice.getPayType();
    }

    public void setPayType(String payType) {
        this.invoice.setPayType(payType);
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFaultMessage() {
        return faultMessage;
    }

    public void setFaultMessage(String faultMessage) {
        this.faultMessage = faultMessage;
    }

    @Override
    public String toString() {
        return "InvoicePayResult{" +
                "invoice=" + invoice +
                ", success='" + success + '\'' +
                ", faultMessage='" + faultMessage + '\'' +
                '}';
    }
}
