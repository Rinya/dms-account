package ru.alfastrah.account.sber.backend.model.invoice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.NONE)
public class Invoice {
    @XmlElement(name = "ID")
    private Long id;
    @XmlElement(name = "Pay")
    private BigDecimal pay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPay() {
        return pay;
    }

    public void setPay(BigDecimal pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", pay=" + pay +
                '}';
    }
}
