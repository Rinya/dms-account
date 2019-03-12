package ru.alfastrah.account.sber.model;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.LocalDate;

@SpringComponent
@VaadinSessionScope
public class RegisterData implements Serializable {
    private Insured insured;
    private String password;
    private String retryPassword;
    private String smsCode;
    private boolean rememberMe;

    @Autowired
    public RegisterData(Insured insured) {
        this.insured = insured;
    }

    public Long getUserId() {
        return insured.getUserId();
    }

    public void setUserId(Long userId) {
        this.insured.setUserId(userId);
    }

    public String getSurname() {
        return insured.getSurname();
    }

    public void setSurname(String surname) {
        this.insured.setSurname(surname);
    }

    public String getName() {
        return insured.getName();
    }

    public void setName(String name) {
        this.insured.setName(name);
    }

    public String getPatronymic() {
        return insured.getPatronymic();
    }

    public void setPatronymic(String patronymic) {
        this.insured.setPatronymic(patronymic);
    }

    public LocalDate getBirthDate() {
        return insured.getBirthDate();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.insured.setBirthDate(birthDate);
    }

    public String getPhone() {
        return insured.getPhone();
    }

    public void setPhone(String phone) {
        this.insured.setPhone(phone);
    }

    public String getEmail() {
        return insured.getEmail();
    }

    public void setEmail(String email) {
        this.insured.setEmail(email);
    }

    public String getTableNumber() {
        return insured.getTableNumber();
    }

    public void setTableNumber(String tableNumber) {
        this.insured.setTableNumber(tableNumber);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRetryPassword() {
        return retryPassword;
    }

    public void setRetryPassword(String retryPassword) {
        this.retryPassword = retryPassword;
    }

    public Integer getFranchise() {
        return insured.getFranchise();
    }

    public void setFranchise(Integer franchise) {
        this.insured.setFranchise(franchise);
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public Insured getInsured() {
        return insured;
    }

    @Override
    public String toString() {
        return "RegisterData{" +
                "insured=" + insured +
                ", pass='" + password + '\'' +
                ", retryPass='" + retryPassword + '\'' +
                ", smsCode='" + smsCode + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
