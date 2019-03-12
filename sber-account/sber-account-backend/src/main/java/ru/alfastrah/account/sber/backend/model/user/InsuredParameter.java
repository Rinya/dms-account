package ru.alfastrah.account.sber.backend.model.user;

import java.time.LocalDate;

public class InsuredParameter {
    private String login;
    private String callType;
    private InsuredResult result;
    private String success;
    private String faultMessage;

    public InsuredParameter() {
        this.result = new InsuredResult();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

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

    public String getPolicyNumber() {
        return result.getPolicyNumber();
    }

    public void setPolicyNumber(String policyNumber) {
        this.result.setPolicyNumber(policyNumber);
    }

    public String getSurname() {
        return result.getSurname();
    }

    public void setSurname(String surname) {
        this.result.setSurname(surname);
    }

    public String getName() {
        return result.getName();
    }

    public void setName(String name) {
        this.result.setName(name);
    }

    public String getPatronymic() {
        return result.getPatronymic();
    }

    public void setPatronymic(String patronymic) {
        this.result.setPatronymic(patronymic);
    }

    public LocalDate getBirthDate() {
        return result.getBirthDate();
    }

    public void setBirthDate(LocalDate birthDate) {
        this.result.setBirthDate(birthDate);
    }

    public String getInsurer() {
        return result.getInsurer();
    }

    public void setInsurer(String insurer) {
        this.result.setInsurer(insurer);
    }

    public String getPhone() {
        return result.getPhone();
    }

    public void setPhone(String phone) {
        this.result.setPhone(phone);
    }

    public String getEmail() {
        return result.getEmail();
    }

    public void setEmail(String email) {
        this.result.setEmail(email);
    }

    public String getFilial() {
        return result.getFilial();
    }

    public void setFilial(String filial) {
        this.result.setFilial(filial);
    }

    public String getTableNumber() {
        return result.getTableNumber();
    }

    public void setTableNumber(String tableNumber) {
        this.result.setTableNumber(tableNumber);
    }

    public String getProgrammGroup() {
        return result.getProgrammGroup();
    }

    public void setProgrammGroup(String programmGroup) {
        this.result.setProgrammGroup(programmGroup);
    }

    public LocalDate getBeginDate() {
        return result.getBeginDate();
    }

    public void setBeginDate(LocalDate beginDate) {
        this.result.setBeginDate(beginDate);
    }

    public LocalDate getEndDate() {
        return result.getEndDate();
    }

    public void setEndDate(LocalDate endDate) {
        this.result.setEndDate(endDate);
    }

    public LocalDate getCancelDate() {
        return result.getCancelDate();
    }

    public void setCancelDate(LocalDate cancelDate) {
        this.result.setCancelDate(cancelDate);
    }

    public String getContractNumber() {
        return result.getContractNumber();
    }

    public void setContractNumber(String contractNumber) {
        this.result.setContractNumber(contractNumber);
    }

    public char getRelativeIndicator() {
        return result.getRelativeIndicator();
    }

    public void setRelativeIndicator(char relativeIndicator) {
        this.result.setRelativeIndicator(relativeIndicator);
    }

    public char getFranchise() {
        return result.getFranchise() != 0? 'Y': 'N';
    }

    public void setFranchise(char franchise) {
        this.result.setFranchise('Y' == franchise? 1 : 0);
    }

    public Long getUserId() {
        return result.getUserId();
    }

    public void setUserId(Long userId) {
        this.result.setUserId(userId);
    }

    public Long getPolicyId() {
        return result.getPolicyId();
    }

    public void setPolicyId(Long policyId) {
        this.result.setPolicyId(policyId);
    }

    public Long getHistoryId() {
        return result.getHistoryId();
    }

    public void setHistoryId(Long historyId) {
        this.result.setHistoryId(historyId);
    }

    public Long getProgrammId() {
        return result.getProgrammId();
    }

    public void setProgrammId(Long programmId) {
        this.result.setProgrammId(programmId);
    }

    public InsuredResult getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "InsuredParameter{" +
                "login='" + login + '\'' +
                ", callType='" + callType + '\'' +
                ", result=" + result +
                ", success='" + success + '\'' +
                ", faultMessage='" + faultMessage + '\'' +
                '}';
    }
}
