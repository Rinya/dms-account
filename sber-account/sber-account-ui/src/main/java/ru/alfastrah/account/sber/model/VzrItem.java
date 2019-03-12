package ru.alfastrah.account.sber.model;

import com.vaadin.spring.annotation.UIScope;

import java.time.LocalDate;

@UIScope
public class VzrItem {
    private String fullname;
    private String latinFullName;
    private LocalDate birthDate;
    private String policyDuration;
    private String country;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLatinFullName() {
        return latinFullName;
    }

    public void setLatinFullName(String latinFullName) {
        this.latinFullName = latinFullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPolicyDuration() {
        return policyDuration;
    }

    public void setPolicyDuration(String policyDuration) {
        this.policyDuration = policyDuration;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "VzrItem{" +
                "fullname='" + fullname + '\'' +
                ", latinFullName='" + latinFullName + '\'' +
                ", birthDate=" + birthDate +
                ", policyDuration='" + policyDuration + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
