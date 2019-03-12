package ru.alfastrah.account.sber.model;

import java.math.BigDecimal;

public class PaymentInfo {
    private BigDecimal premium;
    private String info;

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
        return "PaymentInfo{" +
                "premium=" + premium +
                ", info='" + info + '\'' +
                '}';
    }
}
