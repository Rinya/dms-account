package ru.alfastrah.account.sber.backend.model.auth;

import java.time.LocalDate;

public class RegistrationDataWithCheckResult {
    private RegistrationUser user;
    private CheckResult result;

    public RegistrationDataWithCheckResult(RegistrationUser user) {
        this.user = user;
        this.result = new CheckResult();
    }

    public String getPolicyNumber() {
        return user.getPolicyNumber();
    }

    public void setPolicyNumber(String policyNumber) {
        this.user.setPolicyNumber(policyNumber);
    }

    public String getInsuredSurName() {
        return user.getInsuredSurName();
    }

    public void setInsuredSurName(String insuredSurName) {
        this.user.setInsuredSurName(insuredSurName);
    }

    public String getInsuredFirstName() {
        return user.getInsuredFirstName();
    }

    public void setInsuredFirstName(String insuredFirstName) {
        this.user.setInsuredFirstName(insuredFirstName);
    }

    public String getInsuredPatronymic() {
        return user.getInsuredPatronymic();
    }

    public void setInsuredPatronymic(String insuredPatronymic) {
        this.user.setInsuredPatronymic(insuredPatronymic);
    }

    public LocalDate getInsuredBirthDate() {
        return user.getInsuredBirthDate();
    }

    public void setInsuredBirthDate(LocalDate insuredBirthDate) {
        this.user.setInsuredBirthDate(insuredBirthDate);
    }

    public String getPhoneNumber() {
        return user.getPhoneNumber();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.user.setPhoneNumber(phoneNumber);
    }

    public Long getUserId() {
        return result.getUserId();
    }

    public void setUserId(Long userId) {
        this.result.setUserId(userId);
    }

    public Integer getFranchise() {
        return result.getFranchise();
    }

    public void setFranchise(Integer franchise) {
        this.result.setFranchise(franchise);
    }

    public String getSuccess() {
        return result.getSuccess();
    }

    public void setSuccess(String success) {
        this.result.setSuccess(success);
    }

    public String getFaultMessage() {
        return result.getFaultMessage();
    }

    public void setFaultMessage(String faultMessage) {
        this.result.setFaultMessage(faultMessage);
    }

    public CheckResult getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "RegistrationDataWithCheckResult{" +
                "user=" + user +
                ", result=" + result +
                '}';
    }
}
