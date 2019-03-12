package ru.alfastrah.account.sber.backend.model.policy;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.NONE)
public class Relative {
    @XmlElement(name = "UserID")
    private Long userID;
    @XmlElement(name = "Pay")
    private BigDecimal premium;
    @XmlElement(name = "ChangeProg")
    private String changeProgramm;
    @XmlElement(name = "ProgrammID")
    private Long programmId;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public String getChangeProgramm() {
        return changeProgramm;
    }

    public void setChangeProgramm(String changeProgramm) {
        this.changeProgramm = changeProgramm;
    }

    public Long getProgrammId() {
        return programmId;
    }

    public void setProgrammId(Long programmId) {
        this.programmId = programmId;
    }

    @Override
    public String toString() {
        return "Relative{" +
                "userID=" + userID +
                ", premium=" + premium +
                ", changeProgramm='" + changeProgramm + '\'' +
                ", programmId=" + programmId +
                '}';
    }
}
