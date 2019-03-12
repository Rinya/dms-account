package ru.alfastrah.account.sber.model;

import ru.alfastrah.account.sber.backend.model.invoice.InvoiceShortData;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceRow {
    private InvoiceShortData invoice;
    private boolean selected;

    public InvoiceRow(InvoiceShortData invoice) {
        this.invoice = invoice;
    }

    public Long getInvoiceId() {
        return invoice.getInvoiceId();
    }

    public void setInvoiceId(Long invoiceId) {
        invoice.setInvoiceId(invoiceId);
    }

    public String getInvoiceNumber() {
        return invoice.getInvoiceNumber();
    }

    public void setInvoiceNumber(String invoiceNumber) {
        invoice.setInvoiceNumber(invoiceNumber);
    }

    public LocalDate getIssueDate() {
        return invoice.getIssueDate();
    }

    public void setIssueDate(LocalDate issueDate) {
        invoice.setIssueDate(issueDate);
    }

    public Long getSubjectId() {
        return invoice.getSubjectId();
    }

    public void setSubjectId(Long subjectId) {
        invoice.setSubjectId(subjectId);
    }

    public String getInsured() {
        return invoice.getInsured();
    }

    public void setInsured(String insured) {
        invoice.setInsured(insured);
    }

    public String getAccount() {
        return invoice.getAccount();
    }

    public void setAccount(String account) {
        invoice.setAccount(account);
    }

    public BigDecimal getPremium() {
        return invoice.getPremium();
    }

    public void setPremium(BigDecimal premium) {
        invoice.setPremium(premium);
    }

    public BigDecimal getDeposit() {
        return invoice.getDeposit();
    }

    public void setDeposit(BigDecimal deposit) {
        invoice.setDeposit(deposit);
    }

    public BigDecimal getPayment() {
        return invoice.getPayment();
    }

    public void setPayment(BigDecimal payment) {
        invoice.setPayment(payment);
    }

    public int getInvoiceStateId() {
        return invoice.getInvoiceStateId();
    }

    public void setInvoiceStateId(int invoiceStateId) {
        invoice.setInvoiceStateId(invoiceStateId);
    }

    public String getInvoiceState() {
        return invoice.getInvoiceState();
    }

    public void setInvoiceState(String invoiceState) {
        invoice.setInvoiceState(invoiceState);
    }

    public InvoiceShortData getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceShortData invoice) {
        this.invoice = invoice;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public String toString() {
        return "InvoiceRow{" +
                "invoice=" + invoice +
                ", selected=" + selected +
                '}';
    }
}
