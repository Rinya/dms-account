package ru.alfastrah.account.sber.backend.model.policy;

import java.math.BigDecimal;

public class InvoiceCreate<T> {
    private T xmlData;
    private Long userId;
    private Long policyId;
    private BigDecimal premium;
    private int payType;


    public T getXmlData() {
        return xmlData;
    }

    public void setXmlData(T xmlData) {
        this.xmlData = xmlData;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    @Override
    public String toString() {
        return "InvoiceCreate{" +
                "xmlData=" + xmlData +
                ", userId=" + userId +
                ", policyId=" + policyId +
                ", premium=" + premium +
                ", payType=" + payType +
                '}';
    }
}
