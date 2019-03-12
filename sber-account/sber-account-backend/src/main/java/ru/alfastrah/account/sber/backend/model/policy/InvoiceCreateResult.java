package ru.alfastrah.account.sber.backend.model.policy;

import java.math.BigDecimal;

public class InvoiceCreateResult {
    private InvoiceCreate invoice;
    private String xml;
    private String success;
    private String faultMessage;
    private Long invoiceId;

    public InvoiceCreateResult(InvoiceCreate invoice) {
        this.invoice = invoice;
    }

    public Long getSubjectId() {
        return invoice.getUserId();
    }

    public void setSubjectId(Long userId) {
        this.invoice.setUserId(userId);
    }

    public Long getPolicyId() {
        return invoice.getPolicyId();
    }

    public void setPolicyId(Long policyId) {
        this.invoice.setPolicyId(policyId);
    }

    public BigDecimal getPremium() {
        return invoice.getPremium();
    }

    public void setPremium(BigDecimal premium) {
        this.invoice.setPremium(premium);
    }

    public int getPayType() {
        return invoice.getPayType();
    }

    public void setPayType(int payType) {
        this.invoice.setPayType(payType);
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
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

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    @Override
    public String toString() {
        return "InvoiceCreateResult{" +
                "invoice=" + invoice +
                ", xml='" + xml + '\'' +
                ", success='" + success + '\'' +
                ", faultMessage='" + faultMessage + '\'' +
                ", invoiceId=" + invoiceId +
                '}';
    }
}
