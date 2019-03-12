package ru.alfastrah.account.sber.backend.model.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceShortData {
    private Long invoiceId;
    private String invoiceNumber;
    private LocalDate issueDate;
    private Long subjectId;
    private String insured;
    private String account;
    private BigDecimal premium;
    private BigDecimal deposit;
    private BigDecimal payment;
    private int invoiceStateId;
    private String invoiceState;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getInsured() {
        return insured;
    }

    public void setInsured(String insured) {
        this.insured = insured;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public BigDecimal getDeposit() {
        return deposit;
    }

    public void setDeposit(BigDecimal deposit) {
        this.deposit = deposit;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public int getInvoiceStateId() {
        return invoiceStateId;
    }

    public void setInvoiceStateId(int invoiceStateId) {
        this.invoiceStateId = invoiceStateId;
    }

    public String getInvoiceState() {
        return invoiceState;
    }

    public void setInvoiceState(String invoiceState) {
        this.invoiceState = invoiceState;
    }

    @Override
    public String toString() {
        return "InvoiceShortData{" +
                "id=" + invoiceId +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", issueDate=" + issueDate +
                ", subjectId=" + subjectId +
                ", insured='" + insured + '\'' +
                ", account='" + account + '\'' +
                ", premium=" + premium +
                ", deposit=" + deposit +
                ", payment=" + payment +
                ", invoiceStateId=" + invoiceStateId +
                ", invoiceState='" + invoiceState + '\'' +
                '}';
    }
}
