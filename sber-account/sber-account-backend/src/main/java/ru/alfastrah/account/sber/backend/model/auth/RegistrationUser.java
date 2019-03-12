package ru.alfastrah.account.sber.backend.model.auth;

import java.time.LocalDate;

public class RegistrationUser {
    private String policyNumber;
    private String insuredSurName;
    private String insuredFirstName;
    private String insuredPatronymic;
    private LocalDate insuredBirthDate;
    private String phoneNumber;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getInsuredSurName() {
        return insuredSurName;
    }

    public void setInsuredSurName(String insuredSurName) {
        this.insuredSurName = insuredSurName;
    }

    public String getInsuredFirstName() {
        return insuredFirstName;
    }

    public void setInsuredFirstName(String insuredFirstName) {
        this.insuredFirstName = insuredFirstName;
    }

    public String getInsuredPatronymic() {
        return insuredPatronymic;
    }

    public void setInsuredPatronymic(String insuredPatronymic) {
        this.insuredPatronymic = insuredPatronymic;
    }

    public LocalDate getInsuredBirthDate() {
        return insuredBirthDate;
    }

    public void setInsuredBirthDate(LocalDate insuredBirthDate) {
        this.insuredBirthDate = insuredBirthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "RegistrationUser{" +
                "policyNumber='" + policyNumber + '\'' +
                ", insuredSurName='" + insuredSurName + '\'' +
                ", insuredFirstName='" + insuredFirstName + '\'' +
                ", insuredPatronymic='" + insuredPatronymic + '\'' +
                ", insuredBirthDate=" + insuredBirthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
