package ru.alfastrah.account.sber.backend.model.auth;

public class CheckResult {
    private Long userId;
    private Integer franchise;
    private String success;
    private String faultMessage;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getFranchise() {
        return franchise;
    }

    public void setFranchise(Integer franchise) {
        this.franchise = franchise;
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

    @Override
    public String toString() {
        return "CheckResult{" +
                "userId=" + userId +
                ", franchise=" + franchise +
                ", success='" + success + '\'' +
                ", faultMessage='" + faultMessage + '\'' +
                '}';
    }
}
