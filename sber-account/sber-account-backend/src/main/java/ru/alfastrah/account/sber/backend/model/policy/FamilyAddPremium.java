package ru.alfastrah.account.sber.backend.model.policy;

import java.math.BigDecimal;

public class FamilyAddPremium {
    private String success;
    private String faultMessage;
    private BigDecimal premium;

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

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    @Override
    public String toString() {
        return "FamilyAddPremium{" +
                "success='" + success + '\'' +
                ", faultMessage='" + faultMessage + '\'' +
                ", premium=" + premium +
                '}';
    }
}
