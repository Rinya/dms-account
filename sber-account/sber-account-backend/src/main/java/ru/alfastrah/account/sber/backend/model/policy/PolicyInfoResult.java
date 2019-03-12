package ru.alfastrah.account.sber.backend.model.policy;

public class PolicyInfoResult {
    private PolicyInfoRequest request;
    private String success;
    private String faultMessage;

    public PolicyInfoResult(PolicyInfoRequest request) {
        this.request = request;
    }

    public String getLogin() {
        return request.getLogin();
    }

    public void setLogin(String login) {
        this.request.setLogin(login);
    }

    public Long getSubjectId() {
        return request.getSubjectId();
    }

    public void setSubjectId(Long subjectId) {
        this.request.setSubjectId(subjectId);
    }

    public Long getPolicyId() {
        return request.getPolicyId();
    }

    public void setPolicyId(Long policyId) {
        this.request.setPolicyId(policyId);
    }

    public Long getProgrammId() {
        return request.getProgrammId();
    }

    public void setProgrammId(Long programmId) {
        this.request.setProgrammId(programmId);
    }

    public Long getHistoryId() {
        return request.getHistoryId();
    }

    public void setHistoryId(Long historyId) {
        this.request.setHistoryId(historyId);
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
        return "PolicyInfoResult{" +
                "request=" + request +
                ", success='" + success + '\'' +
                ", faultMessage='" + faultMessage + '\'' +
                '}';
    }
}
