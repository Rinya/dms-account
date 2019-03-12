package ru.alfastrah.account.sber.backend.model.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceDetail {
    private int id;
    private String hospital;
    private LocalDate requestDate;
    private String medicalService;
    private int serviceCount;
    private BigDecimal premium;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public String getMedicalService() {
        return medicalService;
    }

    public void setMedicalService(String medicalService) {
        this.medicalService = medicalService;
    }

    public int getServiceCount() {
        return serviceCount;
    }

    public void setServiceCount(int serviceCount) {
        this.serviceCount = serviceCount;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    @Override
    public String toString() {
        return "InvoiceDetail{" +
                "id=" + id +
                ", hospital='" + hospital + '\'' +
                ", requestDate=" + requestDate +
                ", medicalService='" + medicalService + '\'' +
                ", serviceCount=" + serviceCount +
                ", premium=" + premium +
                '}';
    }
}
